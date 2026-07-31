package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food_recipes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "prep_time_minutes")
    private Integer prepTimeMinutes;

    @Column(name = "cook_time_minutes")
    private Integer cookTimeMinutes;

    @Column(length = 255)
    private String imageUrl;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "community_id")
    private Long communityId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipe_id")
    @Builder.Default
    private List<RecipeComment> comments = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
