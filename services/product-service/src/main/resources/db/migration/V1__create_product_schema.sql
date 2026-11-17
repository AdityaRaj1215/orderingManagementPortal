CREATE TABLE plans (
    id UUID PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT chk_plan_status CHECK (status IN ('ACTIVE', 'INACTIVE'))
);

CREATE TABLE plan_prices (
    id UUID PRIMARY KEY,
    plan_id UUID NOT NULL REFERENCES plans(id),
    version INTEGER NOT NULL,
    amount NUMERIC(12,2) NOT NULL,
    currency CHAR(3) NOT NULL,
    billing_interval VARCHAR(20) NOT NULL,
    effective_from TIMESTAMPTZ NOT NULL,
    effective_to TIMESTAMPTZ,
    CONSTRAINT uq_plan_price_version UNIQUE (plan_id, version),
    CONSTRAINT chk_plan_price_amount CHECK (amount >= 0),
    CONSTRAINT chk_billing_interval CHECK (billing_interval IN ('MONTHLY', 'YEARLY'))
);

CREATE INDEX idx_plan_prices_plan_id ON plan_prices(plan_id);
