package com.manacommunity.api.food.service;

import com.manacommunity.api.food.model.FoodCart;
import com.manacommunity.api.food.repository.FoodCartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FoodCartService unit tests")
class FoodCartServiceTest {

    @Mock private FoodCartRepository cartRepository;
    @InjectMocks private FoodCartService cartService;

    @Test
    @DisplayName("addItem: successfully adds item and computes fees")
    void addItem_success() {
        FoodCart cart = FoodCart.builder()
                .id(1L)
                .communityId(1L)
                .userId(10L)
                .packagingFee(BigDecimal.valueOf(20))
                .deliveryFee(BigDecimal.valueOf(30))
                .build();

        when(cartRepository.findByCommunityIdAndUserId(1L, 10L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(FoodCart.class))).thenAnswer(inv -> inv.getArgument(0));

        FoodCart updated = cartService.addItem(
                1L, 10L, 5L, 101L, "Paneer Biryani", BigDecimal.valueOf(200), 2, "Mild spice");

        assertThat(updated.getRestaurantId()).isEqualTo(5L);
        assertThat(updated.getItems()).hasSize(1);
        assertThat(updated.getSubtotal()).isEqualByComparingTo(BigDecimal.valueOf(400));
        assertThat(updated.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(450));
    }

    @Test
    @DisplayName("addItem: throws when adding item from different restaurant")
    void addItem_multiRestaurant_throwsException() {
        FoodCart cart = FoodCart.builder()
                .id(2L)
                .communityId(1L)
                .userId(10L)
                .restaurantId(5L)
                .build();
        cart.getItems().add(com.manacommunity.api.food.model.FoodCartItem.builder()
                .cart(cart)
                .menuItemId(101L)
                .itemName("Dosa")
                .price(BigDecimal.valueOf(80))
                .quantity(1)
                .build());

        when(cartRepository.findByCommunityIdAndUserId(1L, 10L)).thenReturn(Optional.of(cart));

        assertThatThrownBy(() -> cartService.addItem(
                1L, 10L, 9L, 202L, "Pizza", BigDecimal.valueOf(350), 1, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("another kitchen");
    }

    @Test
    @DisplayName("clearCart: empties all items and resets restaurantId")
    void clearCart_success() {
        FoodCart cart = FoodCart.builder()
                .id(3L)
                .communityId(1L)
                .userId(10L)
                .restaurantId(5L)
                .build();
        cart.getItems().add(com.manacommunity.api.food.model.FoodCartItem.builder()
                .cart(cart)
                .menuItemId(101L)
                .itemName("Pasta")
                .price(BigDecimal.valueOf(250))
                .quantity(1)
                .build());

        when(cartRepository.findByCommunityIdAndUserId(1L, 10L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(FoodCart.class))).thenAnswer(inv -> inv.getArgument(0));

        FoodCart cleared = cartService.clearCart(1L, 10L);

        assertThat(cleared.getItems()).isEmpty();
        assertThat(cleared.getRestaurantId()).isNull();
        assertThat(cleared.getTotalAmount()).isEqualByComparingTo(BigDecimal.ZERO);
    }
}