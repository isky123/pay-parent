package com.pay.api.request;

import com.pay.common.constant.PaymentChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequest {
    @NotBlank
    private String merchantOrderNo;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String currency = "CNY";

    @NotBlank
    private String subject;

    @NotNull
    private PaymentChannel channel;

    private String clientIp;
    private String returnUrl;
    private String notifyUrl;
}
