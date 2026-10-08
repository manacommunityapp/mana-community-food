package com.manacommunity.api.food.controller;

import com.manacommunity.api.food.model.FoodOrder;
import com.manacommunity.api.food.service.KitchenDisplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/food/kds")
@RequiredArgsConstructor
public class KitchenDisplayController {

    private final KitchenDisplayService kdsService;

    @GetMapping("/orders")
    public ResponseEntity<List<FoodOrder>> getLiveKitchenOrders(
            @RequestParam Long restaurantId,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(kdsService.getLiveKitchenOrders(restaurantId, status));
    }

    @PostMapping("/orders/{id}/accept")
    public ResponseEntity<FoodOrder> acceptOrder(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "25") Integer prepMinutes) {
        return ResponseEntity.ok(kdsService.acceptOrder(id, prepMinutes));
    }

    @PostMapping("/orders/{id}/ready")
    public ResponseEntity<FoodOrder> markOrderReady(@PathVariable Long id) {
        return ResponseEntity.ok(kdsService.markOrderReady(id));
    }

    @PostMapping("/orders/{id}/reject")
    public ResponseEntity<FoodOrder> rejectOrder(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {
        String reason = body != null ? body.get("reason") : "KITCHEN_REJECTED";
        return ResponseEntity.ok(kdsService.rejectOrder(id, reason));
    }
}