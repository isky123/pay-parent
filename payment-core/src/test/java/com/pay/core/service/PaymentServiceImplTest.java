package com.pay.core.service;

import com.pay.api.request.CreatePaymentRequest;
import com.pay.api.response.PaymentCreateResponse;
import com.pay.common.constant.PaymentChannel;
import com.pay.common.constant.PaymentStatus;
import com.pay.core.provider.PaymentProviderFactory;
import com.pay.infrastructure.persistence.entity.PaymentOrderDO;
import com.pay.infrastructure.persistence.repository.PaymentOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @Mock
    private PaymentProviderFactory paymentProviderFactory;
    @Mock
    private PaymentOrderRepository paymentOrderRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private CreatePaymentRequest paymentRequest;

    @BeforeEach
    void setUp() {
        paymentRequest = new CreatePaymentRequest();
        paymentRequest.setMerchantOrderNo("ORD202609290001");
        paymentRequest.setAmount(new BigDecimal("99.90"));
        paymentRequest.setCurrency("CNY");
        paymentRequest.setSubject("Test Product");
        paymentRequest.setChannel(PaymentChannel.ALIPAY);
        paymentRequest.setClientIp("127.0.0.1");
    }

    @Test
    void testCreatePaymentSuccess() {
        // Arrange
        PaymentCreateResponse mockResponse = new PaymentCreateResponse();
        mockResponse.setSuccess(true);
        mockResponse.setMerchantOrderNo("ORD202609290001");
        mockResponse.setStatus(PaymentStatus.CREATED);
        mockResponse.setAmount(new BigDecimal("99.90"));

        when(paymentProviderFactory.createPayment(any())).thenReturn(mockResponse);

        // Act
        PaymentCreateResponse response = paymentService.createPayment(paymentRequest);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("ORD202609290001", response.getMerchantOrderNo());
        assertEquals(PaymentStatus.CREATED, response.getStatus());
        assertEquals(new BigDecimal("99.90"), response.getAmount());

        // Verify
        verify(paymentProviderFactory, times(1)).createPayment(any());
        verify(paymentOrderRepository, times(1)).save(any());
    }

    @Test
    void testQueryPaymentSuccess() {
        // Arrange
        PaymentOrderDO order = PaymentOrderDO.builder()
                .merchantOrderNo("ORD202609290001")
                .channel("ALIPAY")
                .amount(new BigDecimal("99.90"))
                .status(PaymentStatus.SUCCESS)
                .build();

        when(paymentOrderRepository.findByMerchantOrderNo("ORD202609290001")).thenReturn(order);

        // Act
        var response = paymentService.queryPayment("ALIPAY", "ORD202609290001");

        // Assert
        assertNotNull(response);
        assertEquals("ORD202609290001", response.getMerchantOrderNo());
        assertEquals(PaymentStatus.SUCCESS, response.getStatus());

        // Verify
        verify(paymentOrderRepository, times(1)).findByMerchantOrderNo("ORD202609290001");
    }

    @Test
    void testQueryPaymentNotFound() {
        // Arrange
        when(paymentOrderRepository.findByMerchantOrderNo("NONEXISTENT")).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            paymentService.queryPayment("ALIPAY", "NONEXISTENT");
        });
    }
}
