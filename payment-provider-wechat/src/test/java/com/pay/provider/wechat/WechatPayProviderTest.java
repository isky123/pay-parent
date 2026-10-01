package com.pay.provider.wechat;

import com.pay.api.request.CreatePaymentRequest;
import com.pay.common.constant.PaymentChannel;
import com.pay.common.constant.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WechatPayProviderTest {
    private final WechatPayProvider provider = new WechatPayProvider();

    @Test
    void testChannel() {
        assertEquals(PaymentChannel.WECHAT, provider.channel());
    }

    @Test
    void testCreatePayment() {
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setMerchantOrderNo("ORD_WECHAT_001");
        request.setAmount(new BigDecimal("88.88"));
        request.setSubject("WeChat Product");

        var response = provider.createPayment(request);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("ORD_WECHAT_001", response.getMerchantOrderNo());
        assertNotNull(response.getPayUrl());
        assertTrue(response.getPayUrl().contains("weixin.qq.com"));
        assertEquals(PaymentStatus.CREATED, response.getStatus());
    }

    @Test
    void testQueryPayment() {
        var response = provider.queryPayment("ORD_WECHAT_001");

        assertNotNull(response);
        assertEquals("ORD_WECHAT_001", response.getMerchantOrderNo());
        assertEquals(PaymentStatus.SUCCESS, response.getStatus());
        assertEquals(PaymentChannel.WECHAT.name(), response.getChannel());
    }
}
