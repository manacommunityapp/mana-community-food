package com.manacommunity.api.food.controller;

import com.manacommunity.api.food.model.FoodRestaurant;
import com.manacommunity.api.food.model.MenuCategory;
import com.manacommunity.api.food.model.MenuItem;
import com.manacommunity.api.food.service.FoodRestaurantService;
import com.manacommunity.api.user.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/food/restaurants")
@RequiredArgsConstructor
public class FoodRestaurantController {

    private final FoodRestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<Page<FoodRestaurant>> getRestaurants(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Long communityId = principal != null ? principal.getCommunityId() : null;
        return ResponseEntity.ok(restaurantService.getRestaurants(page, size, communityId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodRestaurant> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @GetMapping("/mine")
    public ResponseEntity<FoodRestaurant> getMyRestaurant(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(restaurantService.getMyRestaurant(principal.getId()));
    }

    @PostMapping
    public ResponseEntity<FoodRestaurant> createRestaurant(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody FoodRestaurant restaurant) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurant, principal.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodRestaurant> updateRestaurant(
            @PathVariable Long id,
            @RequestBody FoodRestaurant restaurant) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurant));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<FoodRestaurant> updateRestaurantStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String status = body.get("status");
        return ResponseEntity.ok(restaurantService.updateRestaurantStatus(id, status));
    }

    @GetMapping("/featured")
    public ResponseEntity<List<FoodRestaurant>> getFeaturedRestaurants() {
        return ResponseEntity.ok(restaurantService.getFeaturedRestaurants());
    }

    @GetMapping("/{restaurantId}/categories")
    public ResponseEntity<List<MenuCategory>> getCategories(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getCategories(restaurantId));
    }

    @PostMapping("/{restaurantId}/categories")
    public ResponseEntity<MenuCategory> createCategory(
            @PathVariable Long restaurantId,
            @RequestBody MenuCategory category) {
        return ResponseEntity.ok(restaurantService.createCategory(restaurantId, category));
    }

    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<Page<MenuItem>> getMenuItems(
            @PathVariable Long restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long categoryId) {
        return ResponseEntity.ok(restaurantService.getMenuItems(restaurantId, page, size, categoryId));
    }

    @PostMapping("/{restaurantId}/menu")
    public ResponseEntity<MenuItem> createMenuItem(
            @PathVariable Long restaurantId,
            @RequestBody MenuItem item) {
        return ResponseEntity.ok(restaurantService.createMenuItem(restaurantId, item));
    }

    @GetMapping("/{restaurantId}/combos")
    public ResponseEntity<List<MenuItem>> getCombos(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getCombos(restaurantId));
    }
}
