package com.pay.core.service;

import com.pay.api.request.CreateRefundRequest;
import com.pay.common.constant.RefundStatus;
import com.pay.infrastructure.persistence.entity.PaymentOrderDO;
import com.pay.infrastructure.persistence.repository.PaymentOrderRepository;
import com.pay.infrastructure.persistence.repository.RefundOrderRepository;
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
class RefundServiceImplTest {
    @Mock
    private RefundOrderRepository refundOrderRepository;
    @Mock
    private PaymentOrderRepository paymentOrderRepository;

    @InjectMocks
    private RefundServiceImpl refundService;

    private CreateRefundRequest refundRequest;
    private PaymentOrderDO paymentOrder;

    @BeforeEach
    void setUp() {
        refundRequest = new CreateRefundRequest();
        refundRequest.setMerchantOrderNo("ORD202609290001");
        refundRequest.setMerchantRefundNo("REF202609290001");
        refundRequest.setRefundAmount(new BigDecimal("50.00"));
        refundRequest.setReason("User requested refund");

        paymentOrder = PaymentOrderDO.builder()
                .merchantOrderNo("ORD202609290001")
                .channel("ALIPAY")
                .amount(new BigDecimal("99.90"))
                .build();
    }

    @Test
    void testCreateRefundSuccess() {
        // Arrange
        when(paymentOrderRepository.findByMerchantOrderNo("ORD202609290001")).thenReturn(paymentOrder);

        // Act
        var response = refundService.createRefund(refundRequest);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("REF202609290001", response.getMerchantRefundNo());
        assertEquals(RefundStatus.INIT, response.getStatus());
        assertEquals(new BigDecimal("50.00"), response.getRefundAmount());

        // Verify
        verify(paymentOrderRepository, times(1)).findByMerchantOrderNo("ORD202609290001");
        verify(refundOrderRepository, times(1)).save(any());
    }

    @Test
    void testCreateRefundPaymentNotFound() {
        // Arrange
        when(paymentOrderRepository.findByMerchantOrderNo("NONEXISTENT")).thenReturn(null);
        refundRequest.setMerchantOrderNo("NONEXISTENT");

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            refundService.createRefund(refundRequest);
        });
    }

    @Test
    void testCreateRefundExceedsPaymentAmount() {
        // Arrange
        when(paymentOrderRepository.findByMerchantOrderNo("ORD202609290001")).thenReturn(paymentOrder);
        refundRequest.setRefundAmount(new BigDecimal("200.00"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            refundService.createRefund(refundRequest);
        });
    }
}
