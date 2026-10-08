package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "food_chef_profiles", indexes = {
        @Index(name = "idx_chef_community", columnList = "community_id"),
        @Index(name = "idx_chef_user", columnList = "user_id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodChefProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "community_id", nullable = false)
    private Long communityId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "chef_name", nullable = false, length = 150)
    private String chefName;

    @Column(name = "kitchen_name", length = 150)
    private String kitchenName;

    @Column(name = "fssai_license_number", length = 50)
    private String fssaiLicenseNumber;

    @Column(name = "is_verified", nullable = false)
    @Builder.Default
    private Boolean isVerified = false;

    @Column(name = "hygiene_rating")
    @Builder.Default
    private Double hygieneRating = 5.0;

    @Column(name = "kitchen_open_time")
    private LocalTime kitchenOpenTime;

    @Column(name = "kitchen_close_time")
    private LocalTime kitchenCloseTime;

    @Column(name = "daily_portion_capacity")
    @Builder.Default
    private Integer dailyPortionCapacity = 50;

    @Column(length = 255)
    private String specialties;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isVerified == null) isVerified = false;
        if (isActive == null) isActive = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}