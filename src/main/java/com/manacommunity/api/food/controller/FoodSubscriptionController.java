package com.manacommunity.api.food.controller;

import com.manacommunity.api.food.model.FoodSubscription;
import com.manacommunity.api.food.model.SubscriptionPlan;
import com.manacommunity.api.food.service.FoodSubscriptionService;
import com.manacommunity.common.user.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/food/subscriptions")
@RequiredArgsConstructor
public class FoodSubscriptionController {

    private final FoodSubscriptionService subscriptionService;

    @GetMapping("/plans")
    public ResponseEntity<List<SubscriptionPlan>> getPlans(@RequestParam(required = false) Long restaurantId) {
        return ResponseEntity.ok(subscriptionService.getPlans(restaurantId));
    }

    @GetMapping("/plans/{id}")
    public ResponseEntity<SubscriptionPlan> getPlanById(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getPlanById(id));
    }

    @PostMapping("/plans")
    public ResponseEntity<SubscriptionPlan> createPlan(@RequestBody SubscriptionPlan plan) {
        return ResponseEntity.ok(subscriptionService.createPlan(plan));
    }

    @PostMapping
    public ResponseEntity<FoodSubscription> subscribe(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody Map<String, Long> body) {
        Long planId = body.get("planId");
        return ResponseEntity.ok(subscriptionService.subscribe(principal.getId(), planId));
    }

    @GetMapping("/mine")
    public ResponseEntity<List<FoodSubscription>> getMySubscriptions(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(subscriptionService.getMySubscriptions(principal.getId()));
    }

    @PatchMapping("/{id}/pause")
    public ResponseEntity<FoodSubscription> pauseSubscription(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.updateSubscriptionStatus(id, "PAUSED"));
    }

    @PatchMapping("/{id}/resume")
    public ResponseEntity<FoodSubscription> resumeSubscription(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.updateSubscriptionStatus(id, "ACTIVE"));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<FoodSubscription> cancelSubscription(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.updateSubscriptionStatus(id, "CANCELLED"));
    }
}

