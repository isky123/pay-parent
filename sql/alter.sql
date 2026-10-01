-- 修复 payment_order 表结构
ALTER TABLE payment_order ADD COLUMN IF NOT EXISTS version INT DEFAULT 0 AFTER response_json;
ALTER TABLE payment_order MODIFY COLUMN status VARCHAR(32) NOT NULL;

-- 添加缺失的列
ALTER TABLE payment_order ADD COLUMN IF NOT EXISTS return_url VARCHAR(255) AFTER notify_url;
ALTER TABLE payment_order ADD COLUMN IF NOT EXISTS client_ip VARCHAR(64) AFTER return_url;

-- 为性能优化添加索引
CREATE INDEX IF NOT EXISTS idx_payment_status ON payment_order(status);
CREATE INDEX IF NOT EXISTS idx_payment_created_time ON payment_order(create_time);
CREATE INDEX IF NOT EXISTS idx_payment_channel ON payment_order(channel);

-- 修复 refund_order 表
ALTER TABLE refund_order ADD COLUMN IF NOT EXISTS request_json JSON;
ALTER TABLE refund_order ADD COLUMN IF NOT EXISTS response_json JSON;

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_refund_status ON refund_order(status);
CREATE INDEX IF NOT EXISTS idx_refund_merchant_order ON refund_order(merchant_order_no);
