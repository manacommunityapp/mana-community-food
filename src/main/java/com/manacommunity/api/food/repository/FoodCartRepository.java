package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.FoodCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodCartRepository extends JpaRepository<FoodCart, Long> {
    Optional<FoodCart> findByCommunityIdAndUserId(Long communityId, Long userId);
}