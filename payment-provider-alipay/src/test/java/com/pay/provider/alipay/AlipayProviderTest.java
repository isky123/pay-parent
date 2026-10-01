package com.pay.provider.alipay;

import com.pay.api.request.CreatePaymentRequest;
import com.pay.common.constant.PaymentChannel;
import com.pay.common.constant.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AlipayProviderTest {
    private final AlipayProvider provider = new AlipayProvider();

    @Test
    void testChannel() {
        assertEquals(PaymentChannel.ALIPAY, provider.channel());
    }

    @Test
    void testCreatePayment() {
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setMerchantOrderNo("ORD_ALIPAY_001");
        request.setAmount(new BigDecimal("77.77"));
        request.setSubject("Alipay Product");

        var response = provider.createPayment(request);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("ORD_ALIPAY_001", response.getMerchantOrderNo());
        assertNotNull(response.getPayUrl());
        assertTrue(response.getPayUrl().contains("alipay.com"));
        assertEquals(PaymentStatus.CREATED, response.getStatus());
    }

    @Test
    void testQueryPayment() {
        var response = provider.queryPayment("ORD_ALIPAY_001");

        assertNotNull(response);
        assertEquals("ORD_ALIPAY_001", response.getMerchantOrderNo());
        assertEquals(PaymentStatus.SUCCESS, response.getStatus());
        assertEquals(PaymentChannel.ALIPAY.name(), response.getChannel());
    }
}
