package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.ResidentFoodProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentFoodProfileRepository extends JpaRepository<ResidentFoodProfile, Long> {
    Optional<ResidentFoodProfile> findByUserId(Long userId);
}
