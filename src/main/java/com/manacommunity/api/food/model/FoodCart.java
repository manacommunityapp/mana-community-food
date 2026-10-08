package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food_carts", indexes = {
        @Index(name = "idx_food_cart_user", columnList = "community_id, user_id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "community_id", nullable = false)
    private Long communityId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** Enforces single-chef / single-restaurant constraint per cart */
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "packaging_fee", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal packagingFee = BigDecimal.ZERO;

    @Column(name = "delivery_fee", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal deliveryFee = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FoodCartItem> items = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (packagingFee == null) packagingFee = BigDecimal.ZERO;
        if (deliveryFee == null) deliveryFee = BigDecimal.ZERO;
        if (subtotal == null) subtotal = BigDecimal.ZERO;
        if (totalAmount == null) totalAmount = BigDecimal.ZERO;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void recalculateTotals() {
        BigDecimal calculatedSubtotal = BigDecimal.ZERO;
        for (FoodCartItem item : items) {
            if (item.getPrice() != null && item.getQuantity() != null) {
                calculatedSubtotal = calculatedSubtotal.add(
                        item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }
        this.subtotal = calculatedSubtotal;
        BigDecimal pack = this.packagingFee != null ? this.packagingFee : BigDecimal.ZERO;
        BigDecimal deliv = this.deliveryFee != null ? this.deliveryFee : BigDecimal.ZERO;
        this.totalAmount = calculatedSubtotal.add(pack).add(deliv);
    }
}