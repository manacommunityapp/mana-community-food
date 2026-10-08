package com.manacommunity.api.food.controller;

import com.manacommunity.api.food.model.FoodOrder;
import com.manacommunity.api.food.service.FoodDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/food/delivery")
@RequiredArgsConstructor
public class FoodDeliveryController {

    private final FoodDeliveryService deliveryService;

    @PostMapping("/{orderId}/dispatch")
    public ResponseEntity<FoodOrder> dispatchOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(deliveryService.dispatchOrder(orderId));
    }

    @PostMapping("/{orderId}/complete")
    public ResponseEntity<FoodOrder> completeDelivery(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> body) {
        String otp = body != null ? body.get("otp") : null;
        return ResponseEntity.ok(deliveryService.verifyAndCompleteDelivery(orderId, otp));
    }

    @GetMapping("/{orderId}/status")
    public ResponseEntity<FoodOrder> getDeliveryStatus(@PathVariable Long orderId) {
        return ResponseEntity.ok(deliveryService.getDeliveryStatus(orderId));
    }
}