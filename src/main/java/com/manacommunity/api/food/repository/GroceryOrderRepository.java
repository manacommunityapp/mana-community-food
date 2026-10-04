package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.GroceryOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryOrderRepository extends JpaRepository<GroceryOrder, Long> {
    Page<GroceryOrder> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    Page<GroceryOrder> findByCommunityIdOrderByCreatedAtDesc(Long communityId, Pageable pageable);
}
