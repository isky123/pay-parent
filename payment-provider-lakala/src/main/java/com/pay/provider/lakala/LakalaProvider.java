package com.pay.provider.lakala;

import com.pay.api.request.CreatePaymentRequest;
import com.pay.api.response.PaymentCreateResponse;
import com.pay.api.response.PaymentQueryResponse;
import com.pay.common.constant.PaymentChannel;
import com.pay.common.constant.PaymentStatus;
import com.pay.core.provider.PaymentProvider;
import org.springframework.stereotype.Component;

@Component
public class LakalaProvider implements PaymentProvider {
    @Override
    public PaymentChannel channel() {
        return PaymentChannel.LAKALA;
    }

    @Override
    public PaymentCreateResponse createPayment(CreatePaymentRequest request) {
        PaymentCreateResponse response = new PaymentCreateResponse();
        response.setSuccess(false);
        response.setMerchantOrderNo(request.getMerchantOrderNo());
        response.setMessage("Lakala adapter placeholder - implement official partner contract here");
        response.setStatus(PaymentStatus.CREATED);
        response.setAmount(request.getAmount());
        return response;
    }

    @Override
    public PaymentQueryResponse queryPayment(String merchantOrderNo) {
        PaymentQueryResponse response = new PaymentQueryResponse();
        response.setMerchantOrderNo(merchantOrderNo);
        response.setStatus(PaymentStatus.CREATED);
        response.setChannel(PaymentChannel.LAKALA.name());
        response.setMessage("Lakala adapter placeholder");
        return response;
    }
}
