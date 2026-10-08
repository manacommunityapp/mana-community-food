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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FoodDeliveryService unit tests")
class FoodDeliveryServiceTest {

    @Mock private FoodOrderRepository orderRepository;
    @InjectMocks private FoodDeliveryService deliveryService;

    @Test
    @DisplayName("dispatchOrder: marks order OUT_FOR_DELIVERY")
    void dispatchOrder_success() {
        FoodOrder order = FoodOrder.builder()
                .id(10L)
                .userId(1L)
                .restaurantId(2L)
                .status("READY")
                .totalAmount(BigDecimal.valueOf(180))
                .build();

        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(FoodOrder.class))).thenAnswer(inv -> inv.getArgument(0));

        FoodOrder updated = deliveryService.dispatchOrder(10L);

        assertThat(updated.getStatus()).isEqualTo("OUT_FOR_DELIVERY");
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("verifyAndCompleteDelivery: verifies OTP and sets DELIVERED status")
    void verifyAndCompleteDelivery_validOtp_success() {
        FoodOrder order = FoodOrder.builder()
                .id(20L)
                .userId(1L)
                .restaurantId(2L)
                .status("OUT_FOR_DELIVERY")
                .deliveryOtp("482910")
                .totalAmount(BigDecimal.valueOf(320))
                .build();

        when(orderRepository.findById(20L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(FoodOrder.class))).thenAnswer(inv -> inv.getArgument(0));

        FoodOrder completed = deliveryService.verifyAndCompleteDelivery(20L, "482910");

        assertThat(completed.getStatus()).isEqualTo("DELIVERED");
        assertThat(completed.getDeliveredAt()).isNotNull();
    }

    @Test
    @DisplayName("verifyAndCompleteDelivery: rejects invalid OTP")
    void verifyAndCompleteDelivery_invalidOtp_throws() {
        FoodOrder order = FoodOrder.builder()
                .id(30L)
                .userId(1L)
                .restaurantId(2L)
                .status("OUT_FOR_DELIVERY")
                .deliveryOtp("482910")
                .totalAmount(BigDecimal.valueOf(320))
                .build();

        when(orderRepository.findById(30L)).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> deliveryService.verifyAndCompleteDelivery(30L, "999999"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid delivery OTP");
    }
}