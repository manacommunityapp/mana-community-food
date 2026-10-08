package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.FoodOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
    Page<FoodOrder> findByUserId(Long userId, Pageable pageable);
    List<FoodOrder> findByRestaurantId(Long restaurantId);
    List<FoodOrder> findByRestaurantIdAndStatus(Long restaurantId, String status);
    List<FoodOrder> findByRestaurantIdAndStatusIn(Long restaurantId, List<String> statuses);
    java.util.Optional<FoodOrder> findByOrderNumber(String orderNumber);
    Page<FoodOrder> findByCommunityIdAndStatus(Long communityId, String status, Pageable pageable);
}
