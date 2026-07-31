package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_subscriptions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE, PAUSED, CANCELLED, EXPIRED

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) status = "ACTIVE";
    }
}
