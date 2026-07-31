package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.FoodEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodEventRepository extends JpaRepository<FoodEvent, Long> {
    Page<FoodEvent> findByCommunityId(Long communityId, Pageable pageable);
}
