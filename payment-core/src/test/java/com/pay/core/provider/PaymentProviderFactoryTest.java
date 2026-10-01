package com.pay.core.provider;

import com.pay.api.request.CreatePaymentRequest;
import com.pay.common.constant.PaymentChannel;
import com.pay.provider.alipay.AlipayProvider;
import com.pay.provider.apple.ApplePayProvider;
import com.pay.provider.paypal.PayPalProvider;
import com.pay.provider.wechat.WechatPayProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PaymentProviderFactoryTest {
    private PaymentProviderFactory factory;

    @BeforeEach
    void setUp() {
        factory = new PaymentProviderFactory(Arrays.asList(
                new WechatPayProvider(),
                new AlipayProvider(),
                new PayPalProvider(),
                new ApplePayProvider()
        ));
    }

    @Test
    void testGetWechatProvider() {
        PaymentProvider provider = factory.getProvider(PaymentChannel.WECHAT);
        assertNotNull(provider);
        assertEquals(PaymentChannel.WECHAT, provider.channel());
    }

    @Test
    void testGetAlipayProvider() {
        PaymentProvider provider = factory.getProvider(PaymentChannel.ALIPAY);
        assertNotNull(provider);
        assertEquals(PaymentChannel.ALIPAY, provider.channel());
    }

    @Test
    void testGetPaypalProvider() {
        PaymentProvider provider = factory.getProvider(PaymentChannel.PAYPAL);
        assertNotNull(provider);
        assertEquals(PaymentChannel.PAYPAL, provider.channel());
    }

    @Test
    void testGetApplePayProvider() {
        PaymentProvider provider = factory.getProvider(PaymentChannel.APPLE_PAY);
        assertNotNull(provider);
        assertEquals(PaymentChannel.APPLE_PAY, provider.channel());
    }

    @Test
    void testUnsupportedChannel() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.getProvider(PaymentChannel.TONGLIAN);
        });
    }

    @Test
    void testCreatePaymentUsingFactory() {
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setMerchantOrderNo("ORD202609290001");
        request.setAmount(new BigDecimal("99.90"));
        request.setChannel(PaymentChannel.ALIPAY);
        request.setSubject("Test Product");

        var response = factory.createPayment(request);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("ORD202609290001", response.getMerchantOrderNo());
    }
}
