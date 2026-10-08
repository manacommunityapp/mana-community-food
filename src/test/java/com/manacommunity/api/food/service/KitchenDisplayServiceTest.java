package com.manacommunity.api.food.service;

import com.manacommunity.api.food.model.FoodOrder;
import com.manacommunity.api.food.repository.FoodOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("KitchenDisplayService unit tests")
class KitchenDisplayServiceTest {

    @Mock private FoodOrderRepository orderRepository;
    @InjectMocks private KitchenDisplayService kdsService;

    @Test
    @DisplayName("acceptOrder: sets status to PREPARING and records prep timer")
    void acceptOrder_success() {
        FoodOrder order = FoodOrder.builder()
                .id(1L)
                .userId(10L)
                .restaurantId(5L)
                .status("PLACED")
                .totalAmount(BigDecimal.valueOf(250))
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(FoodOrder.class))).thenAnswer(inv -> inv.getArgument(0));

        FoodOrder updated = kdsService.acceptOrder(1L, 30);

        assertThat(updated.getStatus()).isEqualTo("PREPARING");
        assertThat(updated.getPreparationMinutes()).isEqualTo(30);
        assertThat(updated.getPrepStartedAt()).isNotNull();
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("markOrderReady: sets status to READY and generates delivery OTP")
    void markOrderReady_generatesOtp() {
        FoodOrder order = FoodOrder.builder()
                .id(2L)
                .userId(10L)
                .restaurantId(5L)
                .status("PREPARING")
                .totalAmount(BigDecimal.valueOf(300))
                .build();

        when(orderRepository.findById(2L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(FoodOrder.class))).thenAnswer(inv -> inv.getArgument(0));

        FoodOrder updated = kdsService.markOrderReady(2L);

        assertThat(updated.getStatus()).isEqualTo("READY");
        assertThat(updated.getReadyAt()).isNotNull();
        assertThat(updated.getDeliveryOtp()).isNotNull().hasSize(6);
    }

    @Test
    @DisplayName("rejectOrder: sets status to CANCELLED and captures cancellation reason")
    void rejectOrder_success() {
        FoodOrder order = FoodOrder.builder()
                .id(3L)
                .userId(10L)
                .restaurantId(5L)
                .status("PLACED")
                .totalAmount(BigDecimal.valueOf(150))
                .build();

        when(orderRepository.findById(3L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(FoodOrder.class))).thenAnswer(inv -> inv.getArgument(0));

        FoodOrder updated = kdsService.rejectOrder(3L, "OUT_OF_STOCK");

        assertThat(updated.getStatus()).isEqualTo("CANCELLED");
        assertThat(updated.getCancellationReason()).isEqualTo("OUT_OF_STOCK");
        assertThat(updated.getCancelledAt()).isNotNull();
    }
}