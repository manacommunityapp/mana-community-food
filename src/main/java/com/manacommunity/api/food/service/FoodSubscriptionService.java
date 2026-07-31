package com.manacommunity.api.food.service;

import com.manacommunity.api.exception.ResourceNotFoundException;
import com.manacommunity.api.food.model.FoodSubscription;
import com.manacommunity.api.food.model.SubscriptionPlan;
import com.manacommunity.api.food.repository.FoodSubscriptionRepository;
import com.manacommunity.api.food.repository.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodSubscriptionService {

    private final SubscriptionPlanRepository planRepository;
    private final FoodSubscriptionRepository subscriptionRepository;

    public List<SubscriptionPlan> getPlans(Long restaurantId) {
        if (restaurantId != null) {
            return planRepository.findByRestaurantIdAndActiveTrue(restaurantId);
        }
        return planRepository.findByActiveTrue();
    }

    public SubscriptionPlan getPlanById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription plan not found with id: " + id));
    }

    @Transactional
    public SubscriptionPlan createPlan(SubscriptionPlan plan) {
        return planRepository.save(plan);
    }

    @Transactional
    public FoodSubscription subscribe(Long userId, Long planId) {
        SubscriptionPlan plan = getPlanById(planId);
        FoodSubscription subscription = FoodSubscription.builder()
                .userId(userId)
                .planId(plan.getId())
                .status("ACTIVE")
                .build();
        return subscriptionRepository.save(subscription);
    }

    public List<FoodSubscription> getMySubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    @Transactional
    public FoodSubscription updateSubscriptionStatus(Long id, String status) {
        FoodSubscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));
        subscription.setStatus(status);
        return subscriptionRepository.save(subscription);
    }
}
