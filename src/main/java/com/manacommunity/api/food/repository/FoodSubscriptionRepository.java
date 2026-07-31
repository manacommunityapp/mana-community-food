package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.FoodSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodSubscriptionRepository extends JpaRepository<FoodSubscription, Long> {
    List<FoodSubscription> findByUserId(Long userId);
    List<FoodSubscription> findByUserIdAndStatus(Long userId, String status);
}
