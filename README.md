# Pay Parent - 企业级聚合支付系统

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
![Java](https://img.shields.io/badge/Java-17+-brightgreen.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0+-brightgreen.svg)

## 📋 项目介绍

`pay-parent` 是一个企业级、高可用、易扩展的聚合支付平台，支持多个国内外主流支付渠道的统一接入与管理。

### 核心特性

- ✅ **统一支付接口** - 业务系统只需调用一套 API，自动适配多个支付渠道
- ✅ **支付渠道支持**
  - 微信支付 (WeChat Pay)
  - 支付宝 (Alipay)
  - PayPal
  - Apple Pay
  - 通联支付 (预留接口)
  - 拉卡拉 (预留接口)
- ✅ **完整支付流程** - 支付、退款、查询、对账、通知处理
- ✅ **企业级特性**
  - 幂等性保证
  - 分布式事务处理
  - 异步回调与补单机制
  - 状态机严格流转
  - 金额精度控制 (BigDecimal)
  - 签名验证与安全加密
- ✅ **高可用架构**
  - 消息队列异步解耦
  - Redis 分布式缓存
  - 数据库乐观锁
  - 重试与补偿机制
- ✅ **运维友好**
  - 完整的 Docker 容器化部署
  - 数据库迁移脚本
  - 系统配置中心
  - 实时监控与日志

## 📦 项目结构

```
pay-parent
├── README.md                           # 项目说明文档
├── pom.xml                            # Maven 父工程配置
│
├── payment-common/                    # 通用模块
│   ├── pom.xml
│   └── src/main/java
│       └── com/payment/common/
│           ├── entity/                # 通用实体
│           ├── enums/                 # 枚举定义
│           ├── exception/             # 自定义异常
│           ├── utils/                 # 工具类
│           └── constant/              # 常量定义
│
├── payment-api/                       # 对外 API 定义
│   ├── pom.xml
│   └── src/main/java
│       └── com/payment/api/
│           ├── dto/                   # 数据传输对象
│           ├── request/               # 请求参数
│           ├── response/              # 响应参数
│
├── payment-core/                      # 核心业务逻辑
│   ├── pom.xml
│   └── src/main/java
│       └── com/payment/core/
│           ├── service/               # 业务服务
│           ├── provider/              # 支付渠道接口
│           ├── handler/               # 业务处理器
│           └── strategy/              # 策略实现
│
├── payment-infrastructure/            # 基础设施
│   ├── pom.xml
│   └── src/main/java
│       └── com/payment/infrastructure/
│           ├── persistence/           # 数据持久化
│           ├── mq/                    # 消息队列集成
│           ├── cache/                 # 缓存处理
│           └── config/                # 配置管理
│
├── payment-provider-wechat/           # 微信支付适配器
│   ├── pom.xml
│   └── src/main/java
│       └── com/payment/wechat/
│
├── payment-provider-alipay/           # 支付宝适配器
│   ├── pom.xml
│   └── src/main/java
│       └── com/payment/alipay/
│
├── payment-provider-paypal/           # PayPal 适配器
│   ├── pom.xml
│   └── src/main/java
│       └── com/payment/paypal/
│
├── payment-provider-apple/            # Apple Pay 适配器
│   ├── pom.xml
│   └── src/main/java
│       └── com/payment/apple/
│
├── payment-provider-tonglian/         # 通联支付适配器 (预留)
│   ├── pom.xml
│   └── src/main/java
│       └── com/payment/tonglian/
│
├── payment-provider-lakala/           # 拉卡拉适配器 (预留)
│   ├── pom.xml
│   └── src/main/java
│       └── com/payment/lakala/
│
├── payment-web/                       # Web 应用
│   ├── pom.xml
│   ├── src/main/java
│   │   └── com/payment/web/
│   │       ├── controller/            # REST API 控制器
│   │       ├── config/                # Web 配置
│   │       └── exception/             # 异常处理
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   ├── application-dev.yml
│   │   ├── application-prod.yml
│   │   └── logback-spring.xml
│   └── src/main/webapp/               # 前端页面
│       └── index.html
│
├── payment-admin/                     # 管理后台 (可选)
│   ├── pom.xml
│   └── src/main/java
│
├── payment-front/                     # 前端页面 (测试)
│   ├── index.html
│   ├── js/
│   │   └── payment.js
│   └── css/
│       └── style.css
│
├── sql/                               # 数据库脚本
│   ├── schema.sql                     # 数据表定义
│   ├── data-init.sql                  # 初始数据
│   └── indexes.sql                    # 索引优化
│
├── docs/                              # 文档
│   ├── architecture.md                # 技术架构设计
│   ├── deployment.md                  # 部署指南
│   ├── api.md                         # API 文档
│   ├── database.md                    # 数据库设计
│   └── provider/                      # 各支付商接入指南
│       ├── wechat.md
│       ├── alipay.md
│       ├── paypal.md
│       ├── apple.md
│       ├── tonglian.md
│       └── lakala.md
│
└── docker/                            # Docker 配置
    ├── Dockerfile
    ├── docker-compose.yml
    └── nginx.conf
```

## 🚀 快速开始

### 1. 环境要求

- Java 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+
- RabbitMQ 3.8+

### 2. 克隆与编译

```bash
git clone https://github.com/isky123/pay-parent.git
cd pay-parent

# 编译项目
mvn clean install -DskipTests

# 运行应用
cd payment-web
mvn spring-boot:run
```

### 3. 数据库初始化

```bash
# 创建数据库
mysql -u root -p < sql/schema.sql

# 初始化数据
mysql -u root -p < sql/data-init.sql

# 创建索引
mysql -u root -p < sql/indexes.sql
```

### 4. 配置支付渠道

编辑 `payment-web/src/main/resources/application.yml`：

```yaml
payment:
  wechat:
    enabled: true
    app-id: your_wechat_app_id
    mch-id: your_wechat_mch_id
    api-v3-key: your_wechat_api_v3_key
    
  alipay:
    enabled: true
    app-id: your_alipay_app_id
    private-key: your_alipay_private_key
    
  paypal:
    enabled: true
    client-id: your_paypal_client_id
    client-secret: your_paypal_client_secret
```

### 5. 访问测试页面

启动应用后，访问：`http://localhost:8080/payment/test`

## 📚 核心 API

### 创建支付

```http
POST /api/v1/payments
Content-Type: application/json
Idempotency-Key: 202609280001

{
  "merchantOrderNo": "ORD202609280001",
  "amount": 99.90,
  "currency": "CNY",
  "subject": "测试商品",
  "channel": "ALIPAY",
  "clientIp": "192.168.1.10",
  "returnUrl": "https://example.com/payment/return",
  "notifyUrl": "https://api.example.com/webhooks/payment"
}
```

### 查询订单

```http
GET /api/v1/payments/ORD202609280001
```

### 发起退款

```http
POST /api/v1/refunds
Content-Type: application/json

{
  "merchantOrderNo": "ORD202609280001",
  "merchantRefundNo": "REF202609280001",
  "refundAmount": 99.90,
  "reason": "用户申请退款"
}
```

### 支付通知

```http
POST /api/v1/webhooks/wechat
POST /api/v1/webhooks/alipay
POST /api/v1/webhooks/paypal
```

## 📖 文档

- [技术架构设计](docs/architecture.md) - 系统架构、核心流程、扩展机制
- [部署指南](docs/deployment.md) - Docker 部署、配置管理、监控告警
- [API 文档](docs/api.md) - 完整 API 参考
- [数据库设计](docs/database.md) - 表结构、索引、性能优化
- [支付宝接入](docs/provider/alipay.md) - Alipay 接入详细步骤
- [微信支付接入](docs/provider/wechat.md) - WeChat Pay 接入详细步骤
- [PayPal 接入](docs/provider/paypal.md) - PayPal 接入详细步骤
- [Apple Pay 接入](docs/provider/apple.md) - Apple Pay 接入详细步骤

## 🔐 安全特性

- ✅ RSA/RSA2 签名验证
- ✅ 请求幂等性保证 (Idempotency-Key)
- ✅ 支付通知去重与验签
- ✅ 敏感数据加密存储
- ✅ 金额精度控制 (BigDecimal)
- ✅ 分布式事务 SAGA 模式
- ✅ 访问控制与权限管理

## 📊 落地阶段规划

### 第一阶段 (当前)
- [x] 统一支付模型
- [x] 订单和退款表
- [x] 微信支付
- [x] 支付宝
- [x] 支付通知处理
- [x] 查询和退款
- [x] 幂等性处理
- [x] 订单状态机

### 第二阶段
- [ ] PayPal 完整集成
- [ ] Apple Pay 完整集成
- [ ] 对账系统
- [ ] MQ 异步通知
- [ ] 定时补单机制
- [ ] 商户配置中心
- [ ] 监控与告警

### 第三阶段
- [ ] 通联支付接入
- [ ] 拉卡拉接入
- [ ] 多商户支持
- [ ] 分账功能
- [ ] 费率管理
- [ ] 运营后台

## 🛠️ 开发指南

### 新增支付渠道

1. 创建新模块 `payment-provider-{channel}`
2. 实现 `PaymentProvider` 接口
3. 配置 Spring Bean
4. 注册到 `PaymentProviderFactory`
5. 编写单元测试

详见 [支付渠道扩展指南](docs/extend-provider.md)

### 贡献代码

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📝 许可证

本项目采用 Apache License 2.0 许可证。详见 [LICENSE](LICENSE)

## 📧 联系方式

- Issue 反馈：https://github.com/isky123/pay-parent/issues
- 邮箱：support@example.com

## 🙏 致谢

感谢所有贡献者和支付平台的官方文档支持。

---

**最后更新**: 2024-09-28
