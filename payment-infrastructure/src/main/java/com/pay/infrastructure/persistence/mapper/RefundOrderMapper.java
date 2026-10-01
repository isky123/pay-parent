package com.pay.infrastructure.persistence.mapper;

import com.pay.infrastructure.persistence.entity.RefundOrderDO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RefundOrderMapper {
    @Insert("INSERT INTO refund_order (merchant_refund_no, merchant_order_no, channel, refund_amount, status, reason, version, create_time, update_time) VALUES (#{merchantRefundNo}, #{merchantOrderNo}, #{channel}, #{refundAmount}, #{status}, #{reason}, 0, NOW(), NOW())")
    void insert(RefundOrderDO refund);

    @Select("SELECT * FROM refund_order WHERE merchant_refund_no = #{merchantRefundNo}")
    RefundOrderDO selectByMerchantRefundNo(String merchantRefundNo);

    @Update("UPDATE refund_order SET status = #{status}, provider_refund_no = #{providerRefundNo}, response_json = #{responseJson}, version = version + 1, update_time = NOW() WHERE merchant_refund_no = #{merchantRefundNo} AND version = #{version}")
    int updateStatus(RefundOrderDO refund);
}
