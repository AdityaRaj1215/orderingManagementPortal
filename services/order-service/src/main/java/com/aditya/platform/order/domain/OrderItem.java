package com.aditya.platform.order.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "plan_id", nullable = false)
    private UUID planId;

    @Column(name = "plan_price_id", nullable = false)
    private UUID planPriceId;

    @Column(name = "plan_name", nullable = false, length = 150)
    private String planName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, length = 3)
    private String currency;

    protected OrderItem() {}

    public OrderItem(UUID planId, UUID planPriceId, String planName,
                     Integer quantity, BigDecimal unitPrice, String currency) {
        this.id = UUID.randomUUID();
        this.planId = planId;
        this.planPriceId = planPriceId;
        this.planName = planName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.currency = currency;
    }

    void setOrder(OrderEntity order) { this.order = order; }
    public UUID getId() { return id; }
    public UUID getPlanId() { return planId; }
    public UUID getPlanPriceId() { return planPriceId; }
    public String getPlanName() { return planName; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public String getCurrency() { return currency; }
}
