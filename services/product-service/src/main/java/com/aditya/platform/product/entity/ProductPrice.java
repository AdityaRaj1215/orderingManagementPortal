package com.aditya.platform.product.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "product_prices")
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillingInterval billingInterval;

    @Column(nullable = false)
    private Instant effectiveFrom;

    protected ProductPrice() {
    }

    public ProductPrice(
            Product product,
            BigDecimal amount,
            String currency,
            BillingInterval billingInterval
    ) {
        this.product = product;
        this.amount = amount;
        this.currency = currency;
        this.billingInterval = billingInterval;
        this.effectiveFrom = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public BillingInterval getBillingInterval() {
        return billingInterval;
    }

    public Instant getEffectiveFrom() {
        return effectiveFrom;
    }
}
