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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    @Builder.Default
    private List<FoodOrderItem> items = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) status = "PLACED";
        if (paymentStatus == null) paymentStatus = "PENDING";
    }
}
