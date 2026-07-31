package com.manacommunity.api.food.service;

import com.manacommunity.api.food.model.ResidentFoodProfile;
import com.manacommunity.api.food.repository.ResidentFoodProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResidentFoodProfileService {

    private final ResidentFoodProfileRepository profileRepository;

    public ResidentFoodProfile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseGet(() -> ResidentFoodProfile.builder()
                        .userId(userId)
                        .dietaryPreference("VEGETARIAN")
                        .aiLifestyleScore(85)
                        .build());
    }

    @Transactional
    public ResidentFoodProfile updateProfile(Long userId, ResidentFoodProfile request) {
        ResidentFoodProfile profile = profileRepository.findByUserId(userId)
                .orElseGet(() -> ResidentFoodProfile.builder().userId(userId).build());

        profile.setDietaryPreference(request.getDietaryPreference());
        profile.setAllergies(request.getAllergies());
        profile.setHealthGoals(request.getHealthGoals());
        if (request.getAiLifestyleScore() != null) {
            profile.setAiLifestyleScore(request.getAiLifestyleScore());
        }
        return profileRepository.save(profile);
    }
}
