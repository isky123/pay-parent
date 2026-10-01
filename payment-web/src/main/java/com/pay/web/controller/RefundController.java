package com.pay.web.controller;

import com.pay.api.request.CreateRefundRequest;
import com.pay.api.response.RefundCreateResponse;
import com.pay.common.dto.ApiResult;
import com.pay.core.service.RefundServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RefundController {
    private final RefundServiceImpl refundService;

    @PostMapping("/refunds")
    public ApiResult<RefundCreateResponse> createRefund(@Valid @RequestBody CreateRefundRequest request) {
        RefundCreateResponse data = refundService.createRefund(request);
        return ApiResult.success(data);
    }
}
