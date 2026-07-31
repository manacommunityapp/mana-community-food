package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "food_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(length = 255)
    private String venue;

    @Column(name = "community_id")
    private Long communityId;

    @Column(name = "organizer_id")
    private Long organizerId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
