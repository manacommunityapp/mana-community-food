package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.FoodRestaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRestaurantRepository extends JpaRepository<FoodRestaurant, Long> {
    Page<FoodRestaurant> findByActiveTrue(Pageable pageable);
    Page<FoodRestaurant> findByCommunityIdAndActiveTrue(Long communityId, Pageable pageable);
    List<FoodRestaurant> findByStatusAndActiveTrue(String status);
    Optional<FoodRestaurant> findByOwnerId(Long ownerId);
}
