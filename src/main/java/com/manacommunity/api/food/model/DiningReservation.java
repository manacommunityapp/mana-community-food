package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "food_dining_reservations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiningReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "guest_count")
    @Builder.Default
    private Integer guestCount = 1;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "CONFIRMED"; // CONFIRMED, CANCELLED, ATTENDED

    @Column(length = 255)
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) status = "CONFIRMED";
        if (guestCount == null) guestCount = 1;
    }
}
