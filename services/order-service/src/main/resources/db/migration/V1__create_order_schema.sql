CREATE TABLE orders (
    id UUID PRIMARY KEY,
    order_number VARCHAR(40) NOT NULL UNIQUE,
    customer_id UUID NOT NULL,
    status VARCHAR(30) NOT NULL,
    total_amount NUMERIC(12,2) NOT NULL,
    currency CHAR(3) NOT NULL,
    idempotency_key VARCHAR(128) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uq_order_customer_idempotency UNIQUE (customer_id, idempotency_key),
    CONSTRAINT chk_order_status CHECK (status IN ('PENDING', 'CONFIRMED', 'FAILED', 'CANCELLED')),
    CONSTRAINT chk_order_amount CHECK (total_amount >= 0)
);

CREATE TABLE order_items (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    plan_id UUID NOT NULL,
    plan_price_id UUID NOT NULL,
    plan_name VARCHAR(150) NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(12,2) NOT NULL,
    currency CHAR(3) NOT NULL,
    CONSTRAINT chk_order_item_quantity CHECK (quantity > 0),
    CONSTRAINT chk_order_item_price CHECK (unit_price >= 0)
);

CREATE INDEX idx_orders_customer_created ON orders(customer_id, created_at DESC);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
