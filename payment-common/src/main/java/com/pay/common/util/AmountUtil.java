package com.pay.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class AmountUtil {
    private AmountUtil() {
    }

    public static BigDecimal toYuan(BigDecimal amount) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        return amount.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal toFen(BigDecimal amount) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        return amount.multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_UP);
    }
}
