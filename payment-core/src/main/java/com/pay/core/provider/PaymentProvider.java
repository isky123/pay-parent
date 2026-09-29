package com.pay.core.provider;

import com.pay.api.request.CreatePaymentRequest;
import com.pay.api.response.PaymentCreateResponse;
import com.pay.api.response.PaymentQueryResponse;
import com.pay.common.constant.PaymentChannel;

public interface PaymentProvider {
    PaymentChannel channel();
    PaymentCreateResponse createPayment(CreatePaymentRequest request);
    PaymentQueryResponse queryPayment(String merchantOrderNo);
}
