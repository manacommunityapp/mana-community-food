package com.manacommunity.api.food.controller;

import com.manacommunity.api.food.model.DiningEvent;
import com.manacommunity.api.food.model.DiningReservation;
import com.manacommunity.api.food.service.DiningService;
import com.manacommunity.common.user.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/food/dining")
@RequiredArgsConstructor
public class DiningController {

    private final DiningService diningService;

    @GetMapping("/events")
    public ResponseEntity<Page<DiningEvent>> getUpcomingEvents(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(diningService.getUpcomingEvents(principal.getCommunityId(), page, size));
    }

    @GetMapping("/events/all")
    public ResponseEntity<Page<DiningEvent>> getAllEvents(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(diningService.getAllEvents(principal.getCommunityId(), page, size));
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<DiningEvent> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(diningService.getEventById(id));
    }

    @PostMapping("/events")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<DiningEvent> createEvent(@RequestBody DiningEvent event) {
        return ResponseEntity.ok(diningService.createEvent(event));
    }

    @PatchMapping("/events/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<DiningEvent> updateEventStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(diningService.updateEventStatus(id, body.get("status")));
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<DiningReservation>> getMyReservations(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(diningService.getUserReservations(principal.getId()));
    }

    @PostMapping("/rsvp")
    public ResponseEntity<DiningReservation> rsvp(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody DiningReservation reservation) {
        return ResponseEntity.ok(diningService.rsvp(principal.getId(), reservation.getEventId(), reservation));
    }

    @PatchMapping("/reservations/{id}/cancel")
    public ResponseEntity<DiningReservation> cancelReservation(@PathVariable Long id) {
        return ResponseEntity.ok(diningService.cancelReservation(id));
    }
}
