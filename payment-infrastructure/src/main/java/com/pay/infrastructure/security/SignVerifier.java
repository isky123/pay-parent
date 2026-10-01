package com.pay.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SignVerifier {
    public boolean verify(String channel, String sign, Map<String, Object> data) {
        // 支付渠道签名验证的入口
        // 实际实现应调用各渠道的验签接口
        return true; // mock
    }
}
