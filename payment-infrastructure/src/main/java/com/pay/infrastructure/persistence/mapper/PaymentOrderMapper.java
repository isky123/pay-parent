package com.pay.infrastructure.persistence.mapper;

import com.pay.infrastructure.persistence.entity.PaymentOrderDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PaymentOrderMapper {
    @Insert("INSERT INTO payment_order (merchant_order_no, channel, merchant_id, amount, currency, subject, status, notify_url, return_url, client_ip, version, create_time, update_time) VALUES (#{merchantOrderNo}, #{channel}, #{merchantId}, #{amount}, #{currency}, #{subject}, #{status}, #{notifyUrl}, #{returnUrl}, #{clientIp}, 0, NOW(), NOW())")
    void insert(PaymentOrderDO order);

    @Select("SELECT * FROM payment_order WHERE merchant_order_no = #{merchantOrderNo}")
    PaymentOrderDO selectByMerchantOrderNo(String merchantOrderNo);

    @Select("SELECT * FROM payment_order WHERE provider_order_no = #{providerOrderNo}")
    PaymentOrderDO selectByProviderOrderNo(String providerOrderNo);

    @Update("UPDATE payment_order SET status = #{status}, provider_order_no = #{providerOrderNo}, response_json = #{responseJson}, version = version + 1, update_time = NOW() WHERE merchant_order_no = #{merchantOrderNo} AND version = #{version}")
    int updateStatus(PaymentOrderDO order);

    @Select("SELECT * FROM payment_order WHERE status IN ('CREATED', 'PAYING') AND expire_time < NOW()")
    List<PaymentOrderDO> selectExpiredOrders();
}
