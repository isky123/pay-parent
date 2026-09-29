# Pay Parent - 企业级聚合支付系统

## 架构目标

本系统采用分层架构：

- payment-common：通用常量、工具类、异常和通用 DTO
- payment-api：请求/返回结构定义
- payment-core：支付统一逻辑和渠道工厂
- payment-infrastructure：Redis、RabbitMQ、数据库等基础设施
- payment-provider-*：各渠道适配器，封装不同 SDK/API
- payment-web：统一对外 HTTP API 与测试页面

## 设计原则

1. 统一订单模型：所有渠道统一抽象为 `PaymentProvider`
2. 统一支付状态机：支付订单状态在一个地方维护
3. 支付目录独立：不同支付渠道分模块管理，避免耦合
4. 预留扩展：通联、拉卡拉按适配器模式预留标准接口
5. 幂等与补偿：所有支付与退款请求支持幂等，最终以状态机管理一致性

## 关键流程

- 创建支付订单
- 选择渠道
- 调用渠道适配器
- 回调验签
- 订单更新状态
- 退款/对账

## 架构示意图

```text
业务系统
   |
   v
payment-web
   |
   v
payment-core
   |
   +-- payment-provider-wechat
   +-- payment-provider-alipay
   +-- payment-provider-paypal
   +-- payment-provider-apple
   +-- payment-provider-tonglian (预留)
   +-- payment-provider-lakala (预留)
   |
   v
payment-infrastructure
   |
   +-- MySQL
   +-- Redis
   +-- RabbitMQ
```
