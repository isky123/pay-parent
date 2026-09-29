# 部署文档

## 环境准备

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+
- RabbitMQ 3.x

## 数据库初始化

```bash
mysql -uroot -p < sql/schema.sql
mysql -uroot -p < sql/init-data.sql
```

## 启动方式

```bash
mvn clean install -DskipTests
cd payment-web
mvn spring-boot:run
```

## Docker 部署示例

```bash
docker-compose up -d
```

## 关键配置

- `application.yml`：统一配置支付渠道密钥和数据库信息
- `env`：生产环境变量应统一注入给容器
- 生产默认不允许在源码仓库直接硬编码密钥

## 生产建议

- 使用 Vault / KMS 等秘密管理服务
- 启用 HTTPS
- 配置灰度发布
- 增强监控、告警和审计
