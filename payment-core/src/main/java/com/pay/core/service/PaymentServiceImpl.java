package com.pay.core.service;

import com.pay.api.request.CreatePaymentRequest;
import com.pay.api.response.PaymentCreateResponse;
import com.pay.api.response.PaymentQueryResponse;
import com.pay.common.constant.PaymentStatus;
import com.pay.core.provider.PaymentProviderFactory;
import com.pay.infrastructure.cache.RedisLockService;
import com.pay.infrastructure.persistence.entity.PaymentOrderDO;
import com.pay.infrastructure.persistence.repository.PaymentOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl {
    private final PaymentProviderFactory paymentProviderFactory;
    private final PaymentOrderRepository paymentOrderRepository;
    private final RedisLockService redisLockService;

    @Transactional
    public PaymentCreateResponse createPayment(CreatePaymentRequest request) {
        String lockKey = "payment:" + request.getMerchantOrderNo();

        // 幂等性检查
        if (!redisLockService.lock(lockKey, "1")) {
            PaymentOrderDO existing = paymentOrderRepository.findByMerchantOrderNo(request.getMerchantOrderNo());
            if (existing != null) {
                log.warn("Payment order already exists: {}", request.getMerchantOrderNo());
                return buildResponseFromOrder(existing);
            }
            throw new RuntimeException("Payment order is being processed");
        }

        try {
            // 创建订单记录
            PaymentOrderDO order = PaymentOrderDO.builder()
                    .merchantOrderNo(request.getMerchantOrderNo())
                    .channel(request.getChannel().name())
                    .amount(request.getAmount())
                    .currency(request.getCurrency())
                    .subject(request.getSubject())
                    .status(PaymentStatus.CREATED)
                    .notifyUrl(request.getNotifyUrl())
                    .returnUrl(request.getReturnUrl())
                    .clientIp(request.getClientIp())
                    .merchantId("M1000001")
                    .version(0)
                    .build();

            paymentOrderRepository.save(order);
            log.info("Payment order created: {}", request.getMerchantOrderNo());

            // 调用支付渠道
            PaymentCreateResponse providerResponse = paymentProviderFactory.createPayment(request);
            providerResponse.setStatus(PaymentStatus.CREATED);

            return providerResponse;
        } finally {
            redisLockService.unlock(lockKey);
        }
    }

    public PaymentQueryResponse queryPayment(String channel, String merchantOrderNo) {
        PaymentOrderDO order = paymentOrderRepository.findByMerchantOrderNo(merchantOrderNo);
        if (order == null) {
            throw new RuntimeException("Payment order not found: " + merchantOrderNo);
        }

        PaymentQueryResponse response = new PaymentQueryResponse();
        response.setMerchantOrderNo(order.getMerchantOrderNo());
        response.setProviderOrderNo(order.getProviderOrderNo());
        response.setStatus(order.getStatus());
        response.setAmount(order.getAmount());
        response.setChannel(order.getChannel());
        return response;
    }

    private PaymentCreateResponse buildResponseFromOrder(PaymentOrderDO order) {
        PaymentCreateResponse response = new PaymentCreateResponse();
        response.setSuccess(true);
        response.setMerchantOrderNo(order.getMerchantOrderNo());
        response.setProviderOrderNo(order.getProviderOrderNo());
        response.setStatus(order.getStatus());
        response.setAmount(order.getAmount());
        return response;
    }
}
