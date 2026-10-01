package com.pay.api.response;

import com.pay.common.constant.RefundStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundCreateResponse {
    private boolean success;
    private String merchantRefundNo;
    private String merchantOrderNo;
    private String providerRefundNo;
    private RefundStatus status;
    private BigDecimal refundAmount;
    private String message;
}
