package com.pay.provider.tonglian;

import com.pay.api.request.CreatePaymentRequest;
import com.pay.api.response.PaymentCreateResponse;
import com.pay.api.response.PaymentQueryResponse;
import com.pay.common.constant.PaymentChannel;
import com.pay.common.constant.PaymentStatus;
import com.pay.core.provider.PaymentProvider;
import org.springframework.stereotype.Component;

@Component
public class TonglianProvider implements PaymentProvider {
    @Override
    public PaymentChannel channel() {
        return PaymentChannel.TONGLIAN;
    }

    @Override
    public PaymentCreateResponse createPayment(CreatePaymentRequest request) {
        PaymentCreateResponse response = new PaymentCreateResponse();
        response.setSuccess(false);
        response.setMerchantOrderNo(request.getMerchantOrderNo());
        response.setMessage("Tonglian adapter placeholder - implement official SDK contract here");
        response.setStatus(PaymentStatus.CREATED);
        response.setAmount(request.getAmount());
        return response;
    }

    @Override
    public PaymentQueryResponse queryPayment(String merchantOrderNo) {
        PaymentQueryResponse response = new PaymentQueryResponse();
        response.setMerchantOrderNo(merchantOrderNo);
        response.setStatus(PaymentStatus.CREATED);
        response.setChannel(PaymentChannel.TONGLIAN.name());
        response.setMessage("Tonglian adapter placeholder");
        return response;
    }
}
