package com.pay.api.response;

import com.pay.common.constant.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentCreateResponse {
    private boolean success;
    private String merchantOrderNo;
    private String providerOrderNo;
    private String payUrl;
    private String qrCodeUrl;
    private String message;
    private PaymentStatus status;
    private BigDecimal amount;
}
