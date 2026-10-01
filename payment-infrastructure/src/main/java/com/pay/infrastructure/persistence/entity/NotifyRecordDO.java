package com.pay.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotifyRecordDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String channel;
    private String merchantOrderNo;
    private String providerOrderNo;
    private String notifyId;
    private String notifyType;
    private String body;
    private String verifyStatus;
    private String processStatus;
    private String errorMessage;
    private LocalDateTime createTime;
}
