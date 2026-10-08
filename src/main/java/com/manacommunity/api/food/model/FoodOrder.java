package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food_orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false, length = 40)
    @Builder.Default
    private String status = "PLACED"; // PLACED, CONFIRMED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED

    @Column(name = "delivery_address", length = 255)
    private String deliveryAddress;

    @Column(name = "payment_method", length = 30)
    private String paymentMethod; // WALLET, UPI, CARD, COD

    @Column(name = "payment_status", length = 30)
    @Builder.Default
    private String paymentStatus = "PENDING"; // PENDING, PAID, FAILED

    @Column(name = "order_number", length = 50)
    private String orderNumber;

    @Column(name = "community_id")
    private Long communityId;

    @Column(name = "provider_type", length = 30)
    @Builder.Default
    private String providerType = "RESTAURANT"; // RESTAURANT, HOME_CHEF, CLOUD_KITCHEN

    @Column(name = "delivery_otp", length = 10)
    private String deliveryOtp;

    @Column(name = "preparation_minutes")
    private Integer preparationMinutes;

    @Column(name = "prep_started_at")
    private LocalDateTime prepStartedAt;

    @Column(name = "ready_at")
    private LocalDateTime readyAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancellation_reason", length = 500)
    private String cancellationReason;

    @Column(name = "delivery_instructions", length = 500)
    private String deliveryInstructions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    @Builder.Default
    private List<FoodOrderItem> items = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = "PLACED";
        if (paymentStatus == null) paymentStatus = "PENDING";
        if (providerType == null) providerType = "RESTAURANT";
        if (orderNumber == null) {
            orderNumber = "ORD-" + System.currentTimeMillis();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
