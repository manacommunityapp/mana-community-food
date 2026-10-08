package com.manacommunity.api.food.service;

import com.manacommunity.api.food.model.FoodCart;
import com.manacommunity.api.food.model.FoodCartItem;
import com.manacommunity.api.food.repository.FoodCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodCartService {

    private final FoodCartRepository cartRepository;

    @Transactional
    public FoodCart getOrCreateCart(Long communityId, Long userId) {
        return cartRepository.findByCommunityIdAndUserId(communityId, userId)
                .orElseGet(() -> cartRepository.save(FoodCart.builder()
                        .communityId(communityId)
                        .userId(userId)
                        .packagingFee(BigDecimal.valueOf(20))
                        .deliveryFee(BigDecimal.valueOf(30))
                        .build()));
    }

    @Transactional
    public FoodCart addItem(Long communityId, Long userId, Long restaurantId,
                            Long menuItemId, String itemName, BigDecimal price,
                            int quantity, String notes) {
        FoodCart cart = getOrCreateCart(communityId, userId);

        // Enforce single-chef/single-kitchen constraint
        if (!cart.getItems().isEmpty() && cart.getRestaurantId() != null
                && !cart.getRestaurantId().equals(restaurantId)) {
            throw new IllegalStateException("Your cart already contains items from another kitchen. Clear your cart before ordering from a different chef/restaurant.");
        }

        cart.setRestaurantId(restaurantId);

        Optional<FoodCartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getMenuItemId().equals(menuItemId))
                .findFirst();

        if (existingItem.isPresent()) {
            FoodCartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            if (notes != null) item.setCustomizationNotes(notes);
        } else {
            FoodCartItem newItem = FoodCartItem.builder()
                    .cart(cart)
                    .menuItemId(menuItemId)
                    .itemName(itemName)
                    .price(price)
                    .quantity(quantity)
                    .customizationNotes(notes)
                    .build();
            cart.getItems().add(newItem);
        }

        cart.recalculateTotals();
        return cartRepository.save(cart);
    }

    @Transactional
    public FoodCart removeItem(Long communityId, Long userId, Long itemId) {
        FoodCart cart = getOrCreateCart(communityId, userId);
        cart.getItems().removeIf(i -> i.getId() != null && i.getId().equals(itemId));
        if (cart.getItems().isEmpty()) {
            cart.setRestaurantId(null);
        }
        cart.recalculateTotals();
        return cartRepository.save(cart);
    }

    @Transactional
    public FoodCart clearCart(Long communityId, Long userId) {
        FoodCart cart = getOrCreateCart(communityId, userId);
        cart.getItems().clear();
        cart.setRestaurantId(null);
        cart.recalculateTotals();
        return cartRepository.save(cart);
    }
}