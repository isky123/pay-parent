package com.pay.common.util;

import java.util.UUID;

public final class IdempotencyUtil {
    private IdempotencyUtil() {
    }

    public static String generateIdempotencyKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
