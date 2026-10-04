package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_grocery_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, length = 50)
    private String category; // VEGETABLES, FRUITS, DAIRY, GRAINS, SPICES, ORGANIC

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 20)
    private String unit; // kg, L, pcs, bunch, dozen

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "is_organic")
    @Builder.Default
    private Boolean isOrganic = false;

    @Column(name = "is_available")
    @Builder.Default
    private Boolean isAvailable = true;

    @Column(name = "stock_quantity")
    @Builder.Default
    private Integer stockQuantity = 0;

    @Column(name = "farmer_name", length = 100)
    private String farmerName;

    @Column(name = "community_id")
    private Long communityId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (isOrganic == null) isOrganic = false;
        if (isAvailable == null) isAvailable = true;
        if (stockQuantity == null) stockQuantity = 0;
    }
}
