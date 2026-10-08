package com.manacommunity.api.food.controller;

import com.manacommunity.api.food.model.FoodCart;
import com.manacommunity.api.food.service.FoodCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/food/cart")
@RequiredArgsConstructor
public class FoodCartController {

    private final FoodCartService cartService;

    @GetMapping
    public ResponseEntity<FoodCart> getCart(
            @RequestParam Long communityId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(cartService.getOrCreateCart(communityId, userId));
    }

    @PostMapping("/items")
    public ResponseEntity<FoodCart> addItem(
            @RequestParam Long communityId,
            @RequestParam Long userId,
            @RequestBody Map<String, Object> req) {
        Long restaurantId = Long.valueOf(req.get("restaurantId").toString());
        Long menuItemId = Long.valueOf(req.get("menuItemId").toString());
        String itemName = (String) req.get("itemName");
        BigDecimal price = new BigDecimal(req.get("price").toString());
        int quantity = req.containsKey("quantity") ? Integer.parseInt(req.get("quantity").toString()) : 1;
        String notes = req.containsKey("notes") ? (String) req.get("notes") : null;

        return ResponseEntity.ok(cartService.addItem(
                communityId, userId, restaurantId, menuItemId, itemName, price, quantity, notes));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<FoodCart> removeItem(
            @RequestParam Long communityId,
            @RequestParam Long userId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItem(communityId, userId, itemId));
    }

    @DeleteMapping
    public ResponseEntity<FoodCart> clearCart(
            @RequestParam Long communityId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(cartService.clearCart(communityId, userId));
    }
}