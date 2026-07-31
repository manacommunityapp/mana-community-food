package com.manacommunity.api.food.service;

import com.manacommunity.api.exception.ResourceNotFoundException;
import com.manacommunity.api.food.model.FoodRestaurant;
import com.manacommunity.api.food.model.MenuCategory;
import com.manacommunity.api.food.model.MenuItem;
import com.manacommunity.api.food.repository.FoodRestaurantRepository;
import com.manacommunity.api.food.repository.MenuCategoryRepository;
import com.manacommunity.api.food.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodRestaurantService {

    private final FoodRestaurantRepository restaurantRepository;
    private final MenuCategoryRepository categoryRepository;
    private final MenuItemRepository menuItemRepository;

    public Page<FoodRestaurant> getRestaurants(int page, int size, Long communityId) {
        PageRequest pageable = PageRequest.of(page, size);
        if (communityId != null) {
            return restaurantRepository.findByCommunityIdAndActiveTrue(communityId, pageable);
        }
        return restaurantRepository.findByActiveTrue(pageable);
    }

    public FoodRestaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }

    public FoodRestaurant getMyRestaurant(Long ownerId) {
        return restaurantRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("No restaurant found for owner id: " + ownerId));
    }

    @Transactional
    public FoodRestaurant createRestaurant(FoodRestaurant restaurant, Long ownerId) {
        restaurant.setOwnerId(ownerId);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public FoodRestaurant updateRestaurant(Long id, FoodRestaurant request) {
        FoodRestaurant existing = getRestaurantById(id);
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setCuisineType(request.getCuisineType());
        existing.setImageUrl(request.getImageUrl());
        existing.setPhone(request.getPhone());
        existing.setEmail(request.getEmail());
        existing.setAddress(request.getAddress());
        return restaurantRepository.save(existing);
    }

    @Transactional
    public FoodRestaurant updateRestaurantStatus(Long id, String status) {
        FoodRestaurant existing = getRestaurantById(id);
        existing.setStatus(status);
        return restaurantRepository.save(existing);
    }

    public List<FoodRestaurant> getFeaturedRestaurants() {
        return restaurantRepository.findByStatusAndActiveTrue("APPROVED");
    }

    public List<MenuCategory> getCategories(Long restaurantId) {
        return categoryRepository.findByRestaurantIdOrderByDisplayOrderAsc(restaurantId);
    }

    @Transactional
    public MenuCategory createCategory(Long restaurantId, MenuCategory category) {
        FoodRestaurant restaurant = getRestaurantById(restaurantId);
        category.setRestaurant(restaurant);
        return categoryRepository.save(category);
    }

    public Page<MenuItem> getMenuItems(Long restaurantId, int page, int size, Long categoryId) {
        PageRequest pageable = PageRequest.of(page, size);
        if (categoryId != null) {
            return menuItemRepository.findByRestaurantIdAndCategoryId(restaurantId, categoryId, pageable);
        }
        return menuItemRepository.findByRestaurantId(restaurantId, pageable);
    }

    @Transactional
    public MenuItem createMenuItem(Long restaurantId, MenuItem item) {
        FoodRestaurant restaurant = getRestaurantById(restaurantId);
        item.setRestaurant(restaurant);
        return menuItemRepository.save(item);
    }

    public List<MenuItem> getCombos(Long restaurantId) {
        return menuItemRepository.findByRestaurantIdAndIsComboTrue(restaurantId);
    }
}
