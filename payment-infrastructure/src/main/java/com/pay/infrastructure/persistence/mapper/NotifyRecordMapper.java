package com.pay.infrastructure.persistence.mapper;

import com.pay.infrastructure.persistence.entity.NotifyRecordDO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NotifyRecordMapper {
    @Insert("INSERT INTO payment_notify_record (channel, merchant_order_no, provider_order_no, notify_id, notify_type, body, verify_status, process_status, create_time) VALUES (#{channel}, #{merchantOrderNo}, #{providerOrderNo}, #{notifyId}, #{notifyType}, #{body}, #{verifyStatus}, #{processStatus}, NOW())")
    void insert(NotifyRecordDO record);

    @Select("SELECT * FROM payment_notify_record WHERE channel = #{channel} AND notify_id = #{notifyId}")
    NotifyRecordDO selectByChannelAndNotifyId(String channel, String notifyId);
}
