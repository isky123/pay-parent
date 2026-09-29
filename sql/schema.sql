CREATE DATABASE IF NOT EXISTS pay_parent CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE pay_parent;

CREATE TABLE IF NOT EXISTS payment_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    merchant_order_no VARCHAR(64) NOT NULL UNIQUE,
    provider_order_no VARCHAR(128),
    channel VARCHAR(32) NOT NULL,
    merchant_id VARCHAR(64),
    amount DECIMAL(18,2) NOT NULL,
    currency VARCHAR(8) NOT NULL DEFAULT 'CNY',
    subject VARCHAR(255) NOT NULL,
    status VARCHAR(32) NOT NULL,
    notify_url VARCHAR(255),
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    pay_time TIMESTAMP NULL,
    expire_time TIMESTAMP NULL,
    request_json JSON,
    response_json JSON
);

CREATE TABLE IF NOT EXISTS refund_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    merchant_refund_no VARCHAR(64) NOT NULL UNIQUE,
    merchant_order_no VARCHAR(64) NOT NULL,
    provider_refund_no VARCHAR(128),
    channel VARCHAR(32) NOT NULL,
    refund_amount DECIMAL(18,2) NOT NULL,
    status VARCHAR(32) NOT NULL,
    reason VARCHAR(255),
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS payment_notify_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    channel VARCHAR(32) NOT NULL,
    merchant_order_no VARCHAR(64) NOT NULL,
    provider_order_no VARCHAR(128),
    notify_type VARCHAR(64),
    body TEXT,
    verify_status VARCHAR(32),
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_payment_channel_status ON payment_order(channel, status);
CREATE INDEX idx_payment_order_no ON payment_order(merchant_order_no);
CREATE INDEX idx_refund_order_no ON refund_order(merchant_order_no);
