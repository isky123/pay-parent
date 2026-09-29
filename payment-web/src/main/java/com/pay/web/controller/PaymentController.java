package com.pay.web.controller;

import com.pay.api.request.CreatePaymentRequest;
import com.pay.api.response.PaymentCreateResponse;
import com.pay.common.dto.ApiResult;
import com.pay.core.service.PaymentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentServiceImpl paymentService;

    @PostMapping("/payments")
    public ApiResult<PaymentCreateResponse> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        PaymentCreateResponse data = paymentService.createPayment(request);
        return ApiResult.success(data);
    }

    @GetMapping("/payments/{merchantOrderNo}")
    public ApiResult<Object> queryPayment(@PathVariable String merchantOrderNo,
                                         @RequestParam(defaultValue = "WECHAT") String channel) {
        return ApiResult.success(paymentService.queryPayment(channel, merchantOrderNo));
    }
}
