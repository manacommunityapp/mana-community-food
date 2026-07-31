package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
    List<SubscriptionPlan> findByActiveTrue();
    List<SubscriptionPlan> findByRestaurantIdAndActiveTrue(Long restaurantId);
}
