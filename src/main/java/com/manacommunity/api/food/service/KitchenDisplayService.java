package com.manacommunity.api.food.service;

import com.manacommunity.common.exception.ResourceNotFoundException;
import com.manacommunity.api.food.model.FoodOrder;
import com.manacommunity.api.food.repository.FoodOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class KitchenDisplayService {

    private final FoodOrderRepository orderRepository;
    private final Random random = new Random();

    @Transactional(readOnly = true)
    public List<FoodOrder> getLiveKitchenOrders(Long restaurantId, String status) {
        if (status != null && !status.isBlank()) {
            return orderRepository.findByRestaurantIdAndStatus(restaurantId, status);
        }
        return orderRepository.findByRestaurantIdAndStatusIn(
                restaurantId, List.of("PLACED", "CONFIRMED", "PREPARING", "READY"));
    }

    @Transactional
    public FoodOrder acceptOrder(Long orderId, Integer prepMinutes) {
        FoodOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("FoodOrder not found: " + orderId));
        order.setStatus("PREPARING");
        order.setPreparationMinutes(prepMinutes != null ? prepMinutes : 25);
        order.setPrepStartedAt(LocalDateTime.now());
        log.info("KDS: Order id={} accepted for prep ({} mins)", orderId, order.getPreparationMinutes());
        return orderRepository.save(order);
    }

    @Transactional
    public FoodOrder markOrderReady(Long orderId) {
        FoodOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("FoodOrder not found: " + orderId));
        order.setStatus("READY");
        order.setReadyAt(LocalDateTime.now());
        if (order.getDeliveryOtp() == null) {
            String otp = String.format("%06d", 100000 + random.nextInt(900000));
            order.setDeliveryOtp(otp);
        }
        log.info("KDS: Order id={} marked READY with delivery OTP generated", orderId);
        return orderRepository.save(order);
    }

    @Transactional
    public FoodOrder rejectOrder(Long orderId, String reason) {
        FoodOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("FoodOrder not found: " + orderId));
        order.setStatus("CANCELLED");
        order.setCancelledAt(LocalDateTime.now());
        order.setCancellationReason(reason != null ? reason : "REJECTED_BY_KITCHEN");
        log.warn("KDS: Order id={} rejected by kitchen, reason={}", orderId, order.getCancellationReason());
        return orderRepository.save(order);
    }
}