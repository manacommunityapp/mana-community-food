package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.PantryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PantryItemRepository extends JpaRepository<PantryItem, Long> {
    List<PantryItem> findByUserIdOrderByExpiryDateAsc(Long userId);

    @Query("SELECT p FROM PantryItem p WHERE p.userId = :userId AND p.expiryDate <= :threshold ORDER BY p.expiryDate ASC")
    List<PantryItem> findExpiringItems(Long userId, LocalDate threshold);

    @Query("SELECT p FROM PantryItem p WHERE p.userId = :userId AND p.quantity <= p.lowStockThreshold")
    List<PantryItem> findLowStockItems(Long userId);
}
