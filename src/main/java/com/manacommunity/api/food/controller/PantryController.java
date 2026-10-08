package com.manacommunity.api.food.controller;

import com.manacommunity.api.food.model.PantryItem;
import com.manacommunity.api.food.service.PantryService;
import com.manacommunity.common.user.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food/pantry")
@RequiredArgsConstructor
public class PantryController {

    private final PantryService pantryService;

    @GetMapping("/items")
    public ResponseEntity<List<PantryItem>> getMyItems(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(pantryService.getUserItems(principal.getId()));
    }

    @GetMapping("/items/expiring")
    public ResponseEntity<List<PantryItem>> getExpiringItems(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(defaultValue = "7") int days) {
        return ResponseEntity.ok(pantryService.getExpiringItems(principal.getId(), days));
    }

    @GetMapping("/items/low-stock")
    public ResponseEntity<List<PantryItem>> getLowStockItems(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(pantryService.getLowStockItems(principal.getId()));
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<PantryItem> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(pantryService.getItemById(id));
    }

    @PostMapping("/items")
    public ResponseEntity<PantryItem> addItem(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody PantryItem item) {
        return ResponseEntity.ok(pantryService.addItem(principal.getId(), item));
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<PantryItem> updateItem(
            @PathVariable Long id,
            @RequestBody PantryItem item) {
        return ResponseEntity.ok(pantryService.updateItem(id, item));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id) {
        pantryService.removeItem(id);
        return ResponseEntity.noContent().build();
    }
}
