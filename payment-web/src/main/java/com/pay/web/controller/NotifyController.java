package com.pay.web.controller;

import com.pay.common.dto.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NotifyController {
    @PostMapping("/webhooks/wechat")
    public ApiResult<String> wechatNotify(@RequestParam String body) {
        return ApiResult.success("success");
    }

    @PostMapping("/webhooks/alipay")
    public ApiResult<String> alipayNotify(@RequestParam String body) {
        return ApiResult.success("success");
    }

    @PostMapping("/webhooks/paypal")
    public ApiResult<String> paypalNotify(@RequestParam String body) {
        return ApiResult.success("success");
    }
}
