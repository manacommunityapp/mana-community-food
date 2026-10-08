package com.manacommunity.api.food.service;

import com.manacommunity.common.exception.ResourceNotFoundException;
import com.manacommunity.api.food.model.FoodOrder;
import com.manacommunity.api.food.repository.FoodOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodDeliveryService {

    private final FoodOrderRepository orderRepository;

    @Transactional
    public FoodOrder dispatchOrder(Long orderId) {
        FoodOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("FoodOrder not found: " + orderId));
        order.setStatus("OUT_FOR_DELIVERY");
        log.info("Order id={} dispatched for delivery", orderId);
        return orderRepository.save(order);
    }

    @Transactional
    public FoodOrder verifyAndCompleteDelivery(Long orderId, String submittedOtp) {
        FoodOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("FoodOrder not found: " + orderId));

        if (order.getDeliveryOtp() != null && !order.getDeliveryOtp().trim().isEmpty()) {
            if (submittedOtp == null || !order.getDeliveryOtp().trim().equals(submittedOtp.trim())) {
                throw new IllegalArgumentException("Invalid delivery OTP provided: " + submittedOtp);
            }
        }

        order.setStatus("DELIVERED");
        order.setDeliveredAt(LocalDateTime.now());
        log.info("Order id={} DELIVERED successfully via OTP verification", orderId);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public FoodOrder getDeliveryStatus(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("FoodOrder not found: " + orderId));
    }
}