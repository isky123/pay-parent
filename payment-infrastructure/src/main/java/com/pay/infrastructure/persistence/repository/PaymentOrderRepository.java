package com.pay.infrastructure.persistence.repository;

import com.pay.infrastructure.persistence.entity.PaymentOrderDO;
import com.pay.infrastructure.persistence.mapper.PaymentOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentOrderRepository {
    private final PaymentOrderMapper mapper;

    public void save(PaymentOrderDO order) {
        mapper.insert(order);
    }

    public PaymentOrderDO findByMerchantOrderNo(String merchantOrderNo) {
        return mapper.selectByMerchantOrderNo(merchantOrderNo);
    }

    public PaymentOrderDO findByProviderOrderNo(String providerOrderNo) {
        return mapper.selectByProviderOrderNo(providerOrderNo);
    }

    public int updateStatus(PaymentOrderDO order) {
        return mapper.updateStatus(order);
    }

    public List<PaymentOrderDO> findExpiredOrders() {
        return mapper.selectExpiredOrders();
    }
}
