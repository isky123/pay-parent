package com.pay.common.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AmountUtilTest {
    @Test
    void testToYuan() {
        BigDecimal amount = new BigDecimal("9990");
        BigDecimal result = AmountUtil.toYuan(amount);
        assertEquals(new BigDecimal("99.90"), result);
    }

    @Test
    void testToFen() {
        BigDecimal amount = new BigDecimal("99.90");
        BigDecimal result = AmountUtil.toFen(amount);
        assertEquals(new BigDecimal("9990"), result);
    }

    @Test
    void testToYuanNull() {
        BigDecimal result = AmountUtil.toYuan(null);
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void testRoundingMode() {
        // 测试舍入模式
        BigDecimal amount = new BigDecimal("9991");
        BigDecimal result = AmountUtil.toYuan(amount);
        assertEquals(new BigDecimal("99.91"), result);
    }
}
