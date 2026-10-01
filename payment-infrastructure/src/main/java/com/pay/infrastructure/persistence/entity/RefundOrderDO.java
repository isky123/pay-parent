package com.pay.infrastructure.persistence.entity;

import com.pay.common.constant.RefundStatus;
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
public class RefundOrderDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String merchantRefundNo;
    private String merchantOrderNo;
    private String providerRefundNo;
    private String channel;
    private BigDecimal refundAmount;
    private RefundStatus status;
    private String reason;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String requestJson;
    private String responseJson;
    private Integer version;
}
