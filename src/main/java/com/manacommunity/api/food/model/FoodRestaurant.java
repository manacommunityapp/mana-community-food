package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food_restaurants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(length = 50)
    private String cuisineType; // INDIAN, CONTINENTAL, ASIAN, BAKERY, CATERING

    @Column(length = 255)
    private String imageUrl;

    @Column(length = 20)
    private String phone;

    @Column(length = 120)
    private String email;

    @Column(length = 255)
    private String address;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "APPROVED"; // PENDING, APPROVED, REJECTED

    @Column(name = "community_id")
    private Long communityId;

    @Column(name = "owner_id")
    private Long ownerId;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MenuCategory> categories = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (active == null) active = true;
        if (status == null) status = "APPROVED";
        if (rating == null) rating = BigDecimal.valueOf(4.5);
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
