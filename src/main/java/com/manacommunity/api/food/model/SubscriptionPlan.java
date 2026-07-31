package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_subscription_plans")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 30)
    private String planType; // DAILY, WEEKLY, MONTHLY

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (active == null) active = true;
    }
}
