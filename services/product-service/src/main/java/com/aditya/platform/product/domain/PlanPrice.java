package com.aditya.platform.product.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "plan_prices")
public class PlanPrice {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_interval", nullable = false, length = 20)
    private BillingInterval billingInterval;

    @Column(name = "effective_from", nullable = false)
    private Instant effectiveFrom;

    @Column(name = "effective_to")
    private Instant effectiveTo;

    protected PlanPrice() {}

    public PlanPrice(Plan plan, int version, BigDecimal amount, String currency,
                     BillingInterval billingInterval) {
        this.id = UUID.randomUUID();
        this.plan = plan;
        this.version = version;
        this.amount = amount;
        this.currency = currency.toUpperCase();
        this.billingInterval = billingInterval;
        this.effectiveFrom = Instant.now();
    }

    public UUID getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public BillingInterval getBillingInterval() { return billingInterval; }
    public Integer getVersion() { return version; }
}
