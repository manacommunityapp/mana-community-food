package com.manacommunity.api.food.controller;

import com.manacommunity.api.food.model.ResidentFoodProfile;
import com.manacommunity.api.food.service.ResidentFoodProfileService;
import com.manacommunity.common.user.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/food/profile")
@RequiredArgsConstructor
public class ResidentFoodProfileController {

    private final ResidentFoodProfileService profileService;

    @GetMapping
    public ResponseEntity<ResidentFoodProfile> getMyProfile(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(profileService.getProfileByUserId(principal.getId()));
    }

    @PutMapping
    public ResponseEntity<ResidentFoodProfile> updateMyProfile(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody ResidentFoodProfile profile) {
        return ResponseEntity.ok(profileService.updateProfile(principal.getId(), profile));
    }
}

