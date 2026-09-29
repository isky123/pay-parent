package com.pay.provider.wechat;

import com.pay.api.request.CreatePaymentRequest;
import com.pay.api.response.PaymentCreateResponse;
import com.pay.api.response.PaymentQueryResponse;
import com.pay.common.constant.PaymentChannel;
import com.pay.common.constant.PaymentStatus;
import com.pay.core.provider.PaymentProvider;
import org.springframework.stereotype.Component;

@Component
public class WechatPayProvider implements PaymentProvider {
    @Override
    public PaymentChannel channel() {
        return PaymentChannel.WECHAT;
    }

    @Override
    public PaymentCreateResponse createPayment(CreatePaymentRequest request) {
        PaymentCreateResponse response = new PaymentCreateResponse();
        response.setSuccess(true);
        response.setMerchantOrderNo(request.getMerchantOrderNo());
        response.setPayUrl("https://pay.weixin.qq.com/redirect?order=" + request.getMerchantOrderNo());
        response.setMessage("WeChat Pay mock payment created");
        response.setStatus(PaymentStatus.CREATED);
        response.setAmount(request.getAmount());
        return response;
    }

    @Override
    public PaymentQueryResponse queryPayment(String merchantOrderNo) {
        PaymentQueryResponse response = new PaymentQueryResponse();
        response.setMerchantOrderNo(merchantOrderNo);
        response.setStatus(PaymentStatus.SUCCESS);
        response.setChannel(PaymentChannel.WECHAT.name());
        response.setMessage("WeChat Pay query success");
        return response;
    }
}
