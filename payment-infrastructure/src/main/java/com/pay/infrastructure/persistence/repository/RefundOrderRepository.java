package com.pay.infrastructure.persistence.repository;

import com.pay.infrastructure.persistence.entity.RefundOrderDO;
import com.pay.infrastructure.persistence.mapper.RefundOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefundOrderRepository {
    private final RefundOrderMapper mapper;

    public void save(RefundOrderDO refund) {
        mapper.insert(refund);
    }

    public RefundOrderDO findByMerchantRefundNo(String merchantRefundNo) {
        return mapper.selectByMerchantRefundNo(merchantRefundNo);
    }

    public int updateStatus(RefundOrderDO refund) {
        return mapper.updateStatus(refund);
    }
}
