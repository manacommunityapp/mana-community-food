package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.DiningReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiningReservationRepository extends JpaRepository<DiningReservation, Long> {
    List<DiningReservation> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<DiningReservation> findByEventId(Long eventId);
    Optional<DiningReservation> findByEventIdAndUserId(Long eventId, Long userId);
    boolean existsByEventIdAndUserIdAndStatusNot(Long eventId, Long userId, String status);
}
