package com.pay.infrastructure.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendPaymentCreatedEvent(String merchantOrderNo) {
        rabbitTemplate.convertAndSend("payment.exchange", "payment.created", merchantOrderNo);
    }

    public void sendPaymentSuccessEvent(String merchantOrderNo) {
        rabbitTemplate.convertAndSend("payment.exchange", "payment.success", merchantOrderNo);
    }

    public void sendRefundEvent(String merchantRefundNo) {
        rabbitTemplate.convertAndSend("payment.exchange", "refund.created", merchantRefundNo);
    }
}
