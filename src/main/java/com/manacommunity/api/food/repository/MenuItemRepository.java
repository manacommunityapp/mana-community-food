package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("foodMenuItemRepository")
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Page<MenuItem> findByRestaurantId(Long restaurantId, Pageable pageable);
    Page<MenuItem> findByRestaurantIdAndCategoryId(Long restaurantId, Long categoryId, Pageable pageable);
    List<MenuItem> findByRestaurantIdAndIsComboTrue(Long restaurantId);
}
