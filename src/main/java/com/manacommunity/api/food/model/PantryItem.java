package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_pantry_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PantryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, length = 20)
    private String unit; // kg, L, pcs, pkt

    @Column(length = 50)
    private String category;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "low_stock_threshold")
    @Builder.Default
    private Integer lowStockThreshold = 1;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (lowStockThreshold == null) lowStockThreshold = 1;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
