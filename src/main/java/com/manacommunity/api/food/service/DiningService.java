package com.manacommunity.api.food.service;

import com.manacommunity.common.exception.ResourceNotFoundException;
import com.manacommunity.api.food.model.DiningEvent;
import com.manacommunity.api.food.model.DiningReservation;
import com.manacommunity.api.food.repository.DiningEventRepository;
import com.manacommunity.api.food.repository.DiningReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiningService {

    private final DiningEventRepository eventRepository;
    private final DiningReservationRepository reservationRepository;

    public Page<DiningEvent> getUpcomingEvents(Long communityId, int page, int size) {
        return eventRepository.findByCommunityIdAndStatusInOrderByEventDateAsc(
                communityId, List.of("UPCOMING", "ONGOING"), PageRequest.of(page, size));
    }

    public Page<DiningEvent> getAllEvents(Long communityId, int page, int size) {
        return eventRepository.findByCommunityIdOrderByEventDateDesc(communityId, PageRequest.of(page, size));
    }

    public DiningEvent getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dining event not found with id: " + id));
    }

    @Transactional
    public DiningEvent createEvent(DiningEvent event) {
        return eventRepository.save(event);
    }

    @Transactional
    public DiningEvent updateEventStatus(Long id, String status) {
        DiningEvent event = getEventById(id);
        event.setStatus(status);
        return eventRepository.save(event);
    }

    public List<DiningReservation> getUserReservations(Long userId) {
        return reservationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public DiningReservation rsvp(Long userId, Long eventId, DiningReservation reservation) {
        if (reservationRepository.existsByEventIdAndUserIdAndStatusNot(eventId, userId, "CANCELLED")) {
            throw new IllegalStateException("Already reserved for this event");
        }
        DiningEvent event = getEventById(eventId);
        if (event.getSpotsTaken() >= event.getMaxSpots()) {
            throw new IllegalStateException("Event is full");
        }
        reservation.setUserId(userId);
        reservation.setEventId(eventId);
        event.setSpotsTaken(event.getSpotsTaken() + reservation.getGuestCount());
        eventRepository.save(event);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public DiningReservation cancelReservation(Long id) {
        DiningReservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        reservation.setStatus("CANCELLED");
        DiningEvent event = getEventById(reservation.getEventId());
        event.setSpotsTaken(Math.max(0, event.getSpotsTaken() - reservation.getGuestCount()));
        eventRepository.save(event);
        return reservationRepository.save(reservation);
    }
}
