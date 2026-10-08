package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.FoodChefProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodChefProfileRepository extends JpaRepository<FoodChefProfile, Long> {
    Optional<FoodChefProfile> findByUserId(Long userId);
    Optional<FoodChefProfile> findByCommunityIdAndUserId(Long communityId, Long userId);
    Page<FoodChefProfile> findByCommunityIdAndIsActiveTrue(Long communityId, Pageable pageable);
    Page<FoodChefProfile> findByCommunityIdAndIsVerifiedTrueAndIsActiveTrue(Long communityId, Pageable pageable);
}