package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.GroceryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
    Page<GroceryItem> findByCommunityIdAndIsAvailableTrue(Long communityId, Pageable pageable);
    Page<GroceryItem> findByCommunityIdAndCategoryAndIsAvailableTrue(Long communityId, String category, Pageable pageable);
    List<GroceryItem> findByCommunityIdAndIsOrganicTrueAndIsAvailableTrue(Long communityId);
}
