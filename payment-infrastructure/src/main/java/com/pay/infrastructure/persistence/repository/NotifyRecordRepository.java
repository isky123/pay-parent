package com.pay.infrastructure.persistence.repository;

import com.pay.infrastructure.persistence.entity.NotifyRecordDO;
import com.pay.infrastructure.persistence.mapper.NotifyRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotifyRecordRepository {
    private final NotifyRecordMapper mapper;

    public void save(NotifyRecordDO record) {
        mapper.insert(record);
    }

    public NotifyRecordDO findByChannelAndNotifyId(String channel, String notifyId) {
        return mapper.selectByChannelAndNotifyId(channel, notifyId);
    }
}
