package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "food_resident_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResidentFoodProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(length = 50)
    private String dietaryPreference; // VEGETARIAN, VEGAN, KETO, JAIN, NON_VEG

    @Column(length = 255)
    private String allergies; // comma separated allergies

    @Column(length = 255)
    private String healthGoals; // WEIGHT_LOSS, MUSCLE_GAIN, HEART_HEALTHY

    @Column(name = "ai_lifestyle_score")
    private Integer aiLifestyleScore;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (aiLifestyleScore == null) aiLifestyleScore = 85;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
