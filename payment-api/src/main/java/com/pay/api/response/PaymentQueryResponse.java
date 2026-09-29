package com.pay.api.response;

import com.pay.common.constant.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentQueryResponse {
    private String merchantOrderNo;
    private String providerOrderNo;
    private PaymentStatus status;
    private BigDecimal amount;
    private String channel;
    private String message;
}
