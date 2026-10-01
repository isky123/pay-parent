package com.pay.core.service;

import com.pay.api.request.CreateRefundRequest;
import com.pay.api.response.RefundCreateResponse;
import com.pay.common.constant.RefundStatus;
import com.pay.infrastructure.persistence.entity.PaymentOrderDO;
import com.pay.infrastructure.persistence.entity.RefundOrderDO;
import com.pay.infrastructure.persistence.repository.PaymentOrderRepository;
import com.pay.infrastructure.persistence.repository.RefundOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundServiceImpl {
    private final RefundOrderRepository refundOrderRepository;
    private final PaymentOrderRepository paymentOrderRepository;

    @Transactional
    public RefundCreateResponse createRefund(CreateRefundRequest request) {
        // 验证原订单
        PaymentOrderDO order = paymentOrderRepository.findByMerchantOrderNo(request.getMerchantOrderNo());
        if (order == null) {
            throw new RuntimeException("Payment order not found: " + request.getMerchantOrderNo());
        }

        // 检查退款金额
        if (request.getRefundAmount().compareTo(order.getAmount()) > 0) {
            throw new RuntimeException("Refund amount exceeds payment amount");
        }

        // 创建退款记录
        RefundOrderDO refund = RefundOrderDO.builder()
                .merchantRefundNo(request.getMerchantRefundNo())
                .merchantOrderNo(request.getMerchantOrderNo())
                .channel(order.getChannel())
                .refundAmount(request.getRefundAmount())
                .status(RefundStatus.INIT)
                .reason(request.getReason())
                .version(0)
                .build();

        refundOrderRepository.save(refund);
        log.info("Refund order created: {}", request.getMerchantRefundNo());

        RefundCreateResponse response = new RefundCreateResponse();
        response.setSuccess(true);
        response.setMerchantRefundNo(request.getMerchantRefundNo());
        response.setMerchantOrderNo(request.getMerchantOrderNo());
        response.setStatus(RefundStatus.INIT);
        response.setRefundAmount(request.getRefundAmount());
        response.setMessage("Refund created successfully");

        return response;
    }
}
