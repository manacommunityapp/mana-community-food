package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_dining_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiningEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 40)
    private String eventType; // POTLUCK, COMMUNITY_DINNER, FESTIVAL, COOKING_CLASS

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(length = 255)
    private String venue;

    @Column(name = "host_name", length = 100)
    private String hostName;

    @Column(name = "host_id")
    private Long hostId;

    @Column(name = "max_spots")
    @Builder.Default
    private Integer maxSpots = 50;

    @Column(name = "spots_taken")
    @Builder.Default
    private Integer spotsTaken = 0;

    @Column(name = "cost_per_person", precision = 10, scale = 2)
    private BigDecimal costPerPerson;

    @Column(name = "is_free")
    @Builder.Default
    private Boolean isFree = false;

    @Column(length = 255)
    private String menu;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "UPCOMING"; // UPCOMING, ONGOING, COMPLETED, CANCELLED

    @Column(name = "community_id")
    private Long communityId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) status = "UPCOMING";
        if (maxSpots == null) maxSpots = 50;
        if (spotsTaken == null) spotsTaken = 0;
        if (isFree == null) isFree = false;
    }
}
