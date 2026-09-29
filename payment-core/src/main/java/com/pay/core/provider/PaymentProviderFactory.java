package com.pay.core.provider;

import com.pay.api.request.CreatePaymentRequest;
import com.pay.api.response.PaymentCreateResponse;
import com.pay.api.response.PaymentQueryResponse;
import com.pay.common.constant.PaymentChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentProviderFactory {
    private final List<PaymentProvider> providers;

    public PaymentProvider getProvider(PaymentChannel channel) {
        Map<PaymentChannel, PaymentProvider> providerMap = providers.stream()
                .collect(Collectors.toMap(PaymentProvider::channel, Function.identity()));

        PaymentProvider provider = providerMap.get(channel);
        if (provider == null) {
            throw new IllegalArgumentException("Unsupported payment channel: " + channel);
        }
        return provider;
    }

    public PaymentCreateResponse createPayment(CreatePaymentRequest request) {
        return getProvider(request.getChannel()).createPayment(request);
    }

    public PaymentQueryResponse queryPayment(PaymentChannel channel, String merchantOrderNo) {
        return getProvider(channel).queryPayment(merchantOrderNo);
    }
}
