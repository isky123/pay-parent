package com.pay.infrastructure.persistence.entity;

import com.pay.common.constant.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOrderDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String merchantOrderNo;
    private String providerOrderNo;
    private String channel;
    private String merchantId;
    private BigDecimal amount;
    private String currency;
    private String subject;
    private PaymentStatus status;
    private String notifyUrl;
    private String returnUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime payTime;
    private LocalDateTime expireTime;
    private String requestJson;
    private String responseJson;
    private String clientIp;
    private Integer version;
}
