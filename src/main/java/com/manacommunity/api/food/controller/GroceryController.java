package com.manacommunity.api.food.controller;

import com.manacommunity.api.food.model.GroceryItem;
import com.manacommunity.api.food.model.GroceryOrder;
import com.manacommunity.api.food.service.GroceryService;
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
@RequestMapping("/api/food/grocery")
@RequiredArgsConstructor
public class GroceryController {

    private final GroceryService groceryService;

    @GetMapping("/items")
    public ResponseEntity<Page<GroceryItem>> getItems(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(groceryService.getItems(principal.getCommunityId(), category, page, size));
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<GroceryItem> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(groceryService.getItemById(id));
    }

    @GetMapping("/items/organic")
    public ResponseEntity<List<GroceryItem>> getOrganicItems(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(groceryService.getOrganicItems(principal.getCommunityId()));
    }

    @PostMapping("/items")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<GroceryItem> createItem(@RequestBody GroceryItem item) {
        return ResponseEntity.ok(groceryService.createItem(item));
    }

    @PutMapping("/items/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<GroceryItem> updateItem(@PathVariable Long id, @RequestBody GroceryItem item) {
        return ResponseEntity.ok(groceryService.updateItem(id, item));
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<GroceryOrder>> getMyOrders(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(groceryService.getUserOrders(principal.getId(), page, size));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<GroceryOrder> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(groceryService.getOrderById(id));
    }

    @PostMapping("/orders")
    public ResponseEntity<GroceryOrder> placeOrder(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody GroceryOrder order) {
        return ResponseEntity.ok(groceryService.placeOrder(principal.getId(), order));
    }

    @PatchMapping("/orders/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<GroceryOrder> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(groceryService.updateOrderStatus(id, body.get("status")));
    }
}
