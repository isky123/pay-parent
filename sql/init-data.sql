INSERT INTO payment_order (
    merchant_order_no,
    provider_order_no,
    channel,
    merchant_id,
    amount,
    currency,
    subject,
    status,
    notify_url,
    request_json,
    response_json
) VALUES (
    'ORD202609290001',
    'WX202609290001',
    'WECHAT',
    'M1000001',
    99.90,
    'CNY',
    '测试商品',
    'CREATED',
    'https://example.com/notify',
    '{"test": true}',
    '{"test": true}'
);
