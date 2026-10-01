package com.pay.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateRefundRequest {
    @NotBlank
    private String merchantOrderNo;

    @NotBlank
    private String merchantRefundNo;

    @NotNull
    private BigDecimal refundAmount;

    private String reason;
}
