package com.manacommunity.api.food.controller;

import com.manacommunity.common.exception.ResourceNotFoundException;
import com.manacommunity.api.food.model.FoodChefProfile;
import com.manacommunity.api.food.repository.FoodChefProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/food/chefs")
@RequiredArgsConstructor
public class FoodChefController {

    private final FoodChefProfileRepository chefRepository;

    @GetMapping
    public ResponseEntity<Page<FoodChefProfile>> getChefs(
            @RequestParam Long communityId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(chefRepository.findByCommunityIdAndIsActiveTrue(communityId, pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<FoodChefProfile> getChefByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(chefRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Chef profile not found for user: " + userId)));
    }

    @PostMapping
    public ResponseEntity<FoodChefProfile> saveChefProfile(@RequestBody FoodChefProfile profile) {
        return ResponseEntity.ok(chefRepository.save(profile));
    }

    @PostMapping("/{id}/verify")
    public ResponseEntity<FoodChefProfile> verifyChef(
            @PathVariable Long id,
            @RequestParam(defaultValue = "true") Boolean verified) {
        FoodChefProfile profile = chefRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chef profile not found: " + id));
        profile.setIsVerified(verified);
        return ResponseEntity.ok(chefRepository.save(profile));
    }
}