package com.manacommunity.api.food.service;

import com.manacommunity.common.exception.ResourceNotFoundException;
import com.manacommunity.api.food.model.GroceryItem;
import com.manacommunity.api.food.model.GroceryOrder;
import com.manacommunity.api.food.repository.GroceryItemRepository;
import com.manacommunity.api.food.repository.GroceryOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroceryService {

    private final GroceryItemRepository itemRepository;
    private final GroceryOrderRepository orderRepository;

    public Page<GroceryItem> getItems(Long communityId, String category, int page, int size) {
        if (category != null && !category.isEmpty()) {
            return itemRepository.findByCommunityIdAndCategoryAndIsAvailableTrue(communityId, category, PageRequest.of(page, size));
        }
        return itemRepository.findByCommunityIdAndIsAvailableTrue(communityId, PageRequest.of(page, size));
    }

    public GroceryItem getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grocery item not found with id: " + id));
    }

    public List<GroceryItem> getOrganicItems(Long communityId) {
        return itemRepository.findByCommunityIdAndIsOrganicTrueAndIsAvailableTrue(communityId);
    }

    @Transactional
    public GroceryItem createItem(GroceryItem item) {
        return itemRepository.save(item);
    }

    @Transactional
    public GroceryItem updateItem(Long id, GroceryItem updated) {
        GroceryItem item = getItemById(id);
        item.setName(updated.getName());
        item.setDescription(updated.getDescription());
        item.setCategory(updated.getCategory());
        item.setPrice(updated.getPrice());
        item.setUnit(updated.getUnit());
        item.setImageUrl(updated.getImageUrl());
        item.setIsOrganic(updated.getIsOrganic());
        item.setIsAvailable(updated.getIsAvailable());
        item.setStockQuantity(updated.getStockQuantity());
        item.setFarmerName(updated.getFarmerName());
        return itemRepository.save(item);
    }

    public Page<GroceryOrder> getUserOrders(Long userId, int page, int size) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size));
    }

    public GroceryOrder getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grocery order not found with id: " + id));
    }

    @Transactional
    public GroceryOrder placeOrder(Long userId, GroceryOrder order) {
        order.setUserId(userId);
        return orderRepository.save(order);
    }

    @Transactional
    public GroceryOrder updateOrderStatus(Long id, String status) {
        GroceryOrder order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
