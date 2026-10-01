# 本地验证清单

## 环境要求

### 必装软件
- **Java 17+** (OpenJDK 或 Oracle JDK)
- **Maven 3.8+** (用于构建)
- **MySQL 8.0+** (数据库)
- **Redis 6.0+** (缓存)
- **RabbitMQ 3.8+** (消息队列，可选)
- **Git** (版本控制)

### 可选工具
- **Docker & Docker Compose** (快速启动依赖)
- **Postman 或 cURL** (API 测试)
- **IDE** (IntelliJ IDEA, VS Code 等)

---

## 第一步：环境初始化

### 1.1 克隆仓库

```bash
git clone https://github.com/isky123/pay-parent.git
cd pay-parent
```

### 1.2 验证 Java 环境

```bash
java -version
# 输出应为 Java 17+ (openjdk version "17.x.x" ...)

mvn -version
# 输出应为 Maven 3.8+
```

### 1.3 启动 MySQL

#### 方式 A：使用 Docker

```bash
docker run -d \
  --name mysql-pay \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -e MYSQL_DATABASE=pay_parent \
  -p 3306:3306 \
  mysql:8.0

# 等待 MySQL 启动 (约 30 秒)
sleep 30
```

#### 方式 B：本地已安装 MySQL

```bash
# 创建数据库
mysql -uroot -p123456 -e "CREATE DATABASE IF NOT EXISTS pay_parent CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```

### 1.4 启动 Redis

#### 方式 A：使用 Docker

```bash
docker run -d \
  --name redis-pay \
  -p 6379:6379 \
  redis:6
```

#### 方式 B：本地已安装 Redis

```bash
redis-server
```

### 1.5 启动 RabbitMQ（可选，但推荐）

```bash
docker run -d \
  --name rabbitmq-pay \
  -e RABBITMQ_DEFAULT_USER=guest \
  -e RABBITMQ_DEFAULT_PASS=guest \
  -p 5672:5672 \
  -p 15672:15672 \
  rabbitmq:3-management

# 管理界面：http://localhost:15672 (guest/guest)
```

---

## 第二步：初始化数据库

### 2.1 执行数据库脚本

```bash
# 创建表
mysql -uroot -p123456 pay_parent < sql/schema.sql

# 初始化数据
mysql -uroot -p123456 pay_parent < sql/init-data.sql

# 应用修正和索引
mysql -uroot -p123456 pay_parent < sql/alter.sql
```

### 2.2 验证数据库

```bash
mysql -uroot -p123456 pay_parent -e "SHOW TABLES;"

# 输出应包含：
# payment_order
# refund_order
# payment_notify_record
```

---

## 第三步：编译项目

### 3.1 清理和编译

```bash
cd pay-parent

# 清理旧的构建
mvn clean

# 编译全部模块
mvn install -DskipTests

# 编译应输出最后一行类似：
# [INFO] BUILD SUCCESS
```

### 3.2 构建 payment-web

```bash
cd payment-web

mvn package -DskipTests

# 成功后在 target/ 目录下应生成：
# payment-web-1.0.0-SNAPSHOT.jar
```

---

## 第四步：运行应用

### 4.1 启动 payment-web

```bash
cd payment-web

# 方式 A：使用 Maven
mvn spring-boot:run

# 方式 B：直接运行 JAR
java -jar target/payment-web-1.0.0-SNAPSHOT.jar

# 成功启动标志：输出包含
# Started PaymentApplication in X seconds (JVM running for X seconds)
```

### 4.2 验证应用健康

```bash
# 新开终端，执行健康检查
curl http://localhost:8080/api/health

# 正确输出：
# {"success":true,"code":"200","message":"success","data":"ok"}
```

---

## 第五步：测试支付功能

### 5.1 前端测试页面

打开浏览器访问：

```
http://localhost:8080/index.html
```

会看到一个交互式的支付测试页面，可以：
1. 选择支付渠道 (微信、支付宝、PayPal、Apple Pay、通联、拉卡拉)
2. 输入订单号
3. 输入金额
4. 点击「发起支付」或「查询订单」

### 5.2 API 接口测试

#### 测试 A：创建支付订单

```bash
curl -X POST http://localhost:8080/api/v1/payments \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: $(uuidgen)" \
  -d '{
    "merchantOrderNo": "ORD202609290001",
    "amount": 99.90,
    "currency": "CNY",
    "subject": "测试商品",
    "channel": "ALIPAY",
    "clientIp": "127.0.0.1",
    "returnUrl": "https://example.com/return",
    "notifyUrl": "https://example.com/notify"
  }'

# 预期输出 (JSON)：
# {
#   "success": true,
#   "code": "200",
#   "message": "success",
#   "data": {
#     "success": true,
#     "merchantOrderNo": "ORD202609290001",
#     "payUrl": "https://openapi.alipay.com/gateway.do?order=ORD202609290001",
#     "status": "CREATED",
#     "amount": 99.90
#   }
# }
```

#### 测试 B：查询订单

```bash
curl -X GET http://localhost:8080/api/v1/payments/ORD202609290001?channel=ALIPAY

# 预期输出：
# {
#   "success": true,
#   "code": "200",
#   "message": "success",
#   "data": {
#     "merchantOrderNo": "ORD202609290001",
#     "status": "SUCCESS",
#     "amount": 99.90,
#     "channel": "ALIPAY"
#   }
# }
```

#### 测试 C：创建退款

```bash
curl -X POST http://localhost:8080/api/v1/refunds \
  -H "Content-Type: application/json" \
  -d '{
    "merchantOrderNo": "ORD202609290001",
    "merchantRefundNo": "REF202609290001",
    "refundAmount": 50.00,
    "reason": "用户申请退款"
  }'

# 预期输出：
# {
#   "success": true,
#   "code": "200",
#   "message": "success",
#   "data": {
#     "success": true,
#     "merchantRefundNo": "REF202609290001",
#     "merchantOrderNo": "ORD202609290001",
#     "status": "INIT",
#     "refundAmount": 50.00,
#     "message": "Refund created successfully"
#   }
# }
```

#### 测试 D：测试各支付渠道

微信支付：
```bash
curl -X POST http://localhost:8080/api/v1/payments \
  -H "Content-Type: application/json" \
  -d '{"merchantOrderNo": "ORD_WECHAT_001", "amount": 88.88, "currency": "CNY", "subject": "微信支付测试", "channel": "WECHAT"}'
```

PayPal：
```bash
curl -X POST http://localhost:8080/api/v1/payments \
  -H "Content-Type: application/json" \
  -d '{"merchantOrderNo": "ORD_PAYPAL_001", "amount": 77.77, "currency": "CNY", "subject": "PayPal测试", "channel": "PAYPAL"}'
```

Apple Pay：
```bash
curl -X POST http://localhost:8080/api/v1/payments \
  -H "Content-Type: application/json" \
  -d '{"merchantOrderNo": "ORD_APPLE_001", "amount": 66.66, "currency": "CNY", "subject": "Apple Pay测试", "channel": "APPLE_PAY"}'
```

---

## 第六步：执行单元测试

### 6.1 运行所有测试

```bash
cd pay-parent

# 运行所有单元测试
mvn test

# 输出应包含：
# [INFO] Tests run: X, Failures: 0, Errors: 0, Skipped: 0
```

### 6.2 运行特定模块的测试

```bash
# 测试 payment-core
mvn -f payment-core/pom.xml test

# 测试 payment-web
mvn -f payment-web/pom.xml test
```

### 6.3 生成测试覆盖率报告

```bash
mvn clean test jacoco:report

# 报告位置：target/site/jacoco/index.html
```

---

## 第七步：数据库验证

### 7.1 检查订单表数据

```bash
mysql -uroot -p123456 pay_parent -e "SELECT * FROM payment_order;" \G

# 应输出初始化的订单数据
```

### 7.2 查看数据库日志

```bash
# 查看是否有表创建成功
mysql -uroot -p123456 pay_parent -e "DESC payment_order;" \G

# 验证字段：merchant_order_no, channel, amount, status, version 等
```

---

## 故障排查

### 问题 1：MySQL 连接失败

```
错误：Communications link failure
```

**解决方案**：
```bash
# 确认 MySQL 运行
mysql -uroot -p123456 -e "SELECT 1;" 

# 检查连接信息
# 应用配置：payment-web/src/main/resources/application.yml
# 确保 host/port/username/password 正确
```

### 问题 2：Redis 连接失败

```
错误：Cannot get a resource, pool error
```

**解决方案**：
```bash
# 确认 Redis 运行
redis-cli ping
# 应输出 PONG

# 检查 application.yml 中的 redis 配置
```

### 问题 3：端口已被占用

```
错误：Port 8080 already in use
```

**解决方案**：
```bash
# 方案 A：更换端口
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"

# 方案 B：杀死占用进程
# macOS/Linux:
lsof -i :8080 | grep LISTEN | awk '{print $2}' | xargs kill -9

# Windows:
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### 问题 4：Maven 编译失败

```
错误：Build failure
```

**解决方案**：
```bash
# 清理 Maven 缓存
rm -rf ~/.m2/repository

# 重新下载依赖
mvn clean install -U
```

### 问题 5：Spring Boot 启动失败

```
错误：Caused by: java.lang.IllegalStateException
```

**解决方案**：
```bash
# 查看详细日志
mvn spring-boot:run -X | head -200

# 检查数据库表是否创建
mysql -uroot -p123456 pay_parent -e "SHOW TABLES;"

# 重新执行 schema.sql
mysql -uroot -p123456 pay_parent < sql/schema.sql
```

---

## 快速验证脚本

### all-in-one.sh (自动化启动)

```bash
#!/bin/bash

set -e

echo "================================"
echo "Pay Parent 环境初始化与验证"
echo "================================"
echo ""

# 1. 启动 MySQL
echo "[1/5] 启动 MySQL..."
docker run -d --name mysql-pay -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=pay_parent -p 3306:3306 mysql:8.0 2>/dev/null || true
sleep 10

# 2. 启动 Redis
echo "[2/5] 启动 Redis..."
docker run -d --name redis-pay -p 6379:6379 redis:6 2>/dev/null || true
sleep 5

# 3. 初始化数据库
echo "[3/5] 初始化数据库..."
mysql -h127.0.0.1 -uroot -p123456 pay_parent < sql/schema.sql
mysql -h127.0.0.1 -uroot -p123456 pay_parent < sql/init-data.sql

# 4. 编译项目
echo "[4/5] 编译项目..."
mvn clean install -DskipTests -q

# 5. 启动应用
echo "[5/5] 启动应用..."
echo "应用启动中，访问 http://localhost:8080/index.html"
cd payment-web && mvn spring-boot:run
```

保存为 `start.sh` 并执行：
```bash
chmod +x start.sh
./start.sh
```

---

## 验证清单

请按顺序检验以下功能，每项后打 ✅：

- [ ] Java 17+ 已安装
- [ ] Maven 3.8+ 已安装
- [ ] MySQL 8.0+ 已启动
- [ ] Redis 6.0+ 已启动
- [ ] 数据库 `pay_parent` 已创建
- [ ] 表 `payment_order` 已创建
- [ ] 表 `refund_order` 已创建
- [ ] `mvn install` 编译成功
- [ ] `payment-web` 应用启动成功
- [ ] 访问 `http://localhost:8080/api/health` 返回 `{"success":true}`
- [ ] 前端页面 `http://localhost:8080/index.html` 可访问
- [ ] 发起支付请求成功 (status 200)
- [ ] 查询订单请求成功 (status 200)
- [ ] 创建退款请求成功 (status 200)
- [ ] 支持多个渠道 (WECHAT, ALIPAY, PAYPAL, APPLE_PAY)
- [ ] 单元测试 `mvn test` 全部通过

---

## 后续行动

验证通过后，建议继续：

1. **集成真实支付 SDK**
   - 微信支付：https://pay.weixin.qq.com/wiki/doc/apiv3/
   - 支付宝：https://opendocs.alipay.com/
   - PayPal：https://developer.paypal.com/
   - Apple Pay：https://developer.apple.com/apple-pay/

2. **完善第二阶段功能**
   - MQ 事件驱动
   - 定时补单
   - 对账系统

3. **第三阶段扩展**
   - 通联支付
   - 拉卡拉支付
   - 多商户支持
   - 分账功能

4. **生产部署**
   - 配置 CI/CD 流程
   - 部署到 Kubernetes
   - 配置监控和告警

---

**需要帮助？** 查看 `README.md` 和 `docs/` 目录下的详细文档。
