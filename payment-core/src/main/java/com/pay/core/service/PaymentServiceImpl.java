package com.pay.core.service;

import com.pay.api.request.CreatePaymentRequest;
import com.pay.api.response.PaymentCreateResponse;
import com.pay.api.response.PaymentQueryResponse;
import com.pay.common.constant.PaymentStatus;
import com.pay.core.provider.PaymentProviderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl {
    private final PaymentProviderFactory paymentProviderFactory;

    public PaymentCreateResponse createPayment(CreatePaymentRequest request) {
        PaymentCreateResponse response = paymentProviderFactory.createPayment(request);
        if (response.getStatus() == null) {
            response.setStatus(PaymentStatus.CREATED);
        }
        return response;
    }

    public PaymentQueryResponse queryPayment(String channel, String merchantOrderNo) {
        return paymentProviderFactory.queryPayment(com.pay.common.constant.PaymentChannel.valueOf(channel), merchantOrderNo);
    }
}
