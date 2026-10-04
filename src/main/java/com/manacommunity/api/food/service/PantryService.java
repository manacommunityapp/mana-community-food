package com.manacommunity.api.food.service;

import com.manacommunity.common.exception.ResourceNotFoundException;
import com.manacommunity.api.food.model.PantryItem;
import com.manacommunity.api.food.repository.PantryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PantryService {

    private final PantryItemRepository pantryRepository;

    public List<PantryItem> getUserItems(Long userId) {
        return pantryRepository.findByUserIdOrderByExpiryDateAsc(userId);
    }

    public List<PantryItem> getExpiringItems(Long userId, int daysAhead) {
        LocalDate threshold = LocalDate.now().plusDays(daysAhead);
        return pantryRepository.findExpiringItems(userId, threshold);
    }

    public List<PantryItem> getLowStockItems(Long userId) {
        return pantryRepository.findLowStockItems(userId);
    }

    public PantryItem getItemById(Long id) {
        return pantryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pantry item not found with id: " + id));
    }

    @Transactional
    public PantryItem addItem(Long userId, PantryItem item) {
        item.setUserId(userId);
        return pantryRepository.save(item);
    }

    @Transactional
    public PantryItem updateItem(Long id, PantryItem updated) {
        PantryItem item = getItemById(id);
        item.setName(updated.getName());
        item.setQuantity(updated.getQuantity());
        item.setUnit(updated.getUnit());
        item.setCategory(updated.getCategory());
        item.setExpiryDate(updated.getExpiryDate());
        item.setLowStockThreshold(updated.getLowStockThreshold());
        item.setImageUrl(updated.getImageUrl());
        return pantryRepository.save(item);
    }

    @Transactional
    public void removeItem(Long id) {
        if (!pantryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pantry item not found with id: " + id);
        }
        pantryRepository.deleteById(id);
    }
}
