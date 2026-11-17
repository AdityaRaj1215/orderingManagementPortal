# Order & Subscription Management Platform

Backend-first Java 21 / Spring Boot microservices project.

## Current milestone

- Product Service: plans and versioned prices
- Order Service: order creation with price snapshots and idempotency
- PostgreSQL: separate databases for each service
- Docker Compose: PostgreSQL, Kafka (KRaft), Redis
- Flyway: schema migrations

Authentication is intentionally not enabled yet. For this first local milestone, the order endpoint accepts a `customerId` in its request. This will be replaced by the authenticated customer identity when Spring Security/JWT is added.

## Prerequisites

- JDK 21
- Docker Desktop / Docker Engine with Compose
- Maven 3.9+ (or add Maven Wrapper)

## Start infrastructure

From the repository root:

```bash
docker compose -f infrastructure/docker-compose.yml up -d
```

Check containers:

```bash
docker compose -f infrastructure/docker-compose.yml ps
```

## Run services

In separate terminals, from repository root:

```bash
mvn -pl services/product-service spring-boot:run
```

```bash
mvn -pl services/order-service spring-boot:run
```

Product Service: `http://localhost:8081`  
Order Service: `http://localhost:8082`

## Create a plan

```bash
curl -X POST http://localhost:8081/api/v1/plans \
  -H "Content-Type: application/json" \
  -d '{
    "code": "PRO_MONTHLY",
    "name": "Professional",
    "description": "Professional monthly subscription plan",
    "amount": 999.00,
    "currency": "INR",
    "billingInterval": "MONTHLY"
  }'
```

Copy the returned `id`.

## Place an order

```bash
curl -X POST http://localhost:8082/api/v1/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: local-checkout-001" \
  -d '{
    "customerId": "c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
    "planId": "PASTE_PLAN_UUID_HERE",
    "quantity": 1
  }'
```

The response is `202 Accepted` and the order is initially `PENDING`. The order stores a snapshot of the plan name, price, price ID and currency so future catalog price changes do not rewrite historical orders.

## Read order

```bash
curl http://localhost:8082/api/v1/orders/PASTE_ORDER_UUID_HERE
```

## Important implementation notes

- Each service owns its database. Do not add cross-service foreign keys.
- Order creation calls Product Service to validate and obtain the current active price.
- The unique `(customer_id, idempotency_key)` constraint is the final duplicate-order protection.
- Kafka and Redis are started locally but are not yet used by the first two services. They will be introduced in later milestones.
- Do not expose this initial unauthenticated development API publicly.

## Next milestone

1. Add customer service and JWT authentication.
2. Add subscription service and lifecycle state machine.
3. Add billing service and deterministic simulated payment provider.
4. Add Kafka events, transactional outbox and inbox deduplication.
