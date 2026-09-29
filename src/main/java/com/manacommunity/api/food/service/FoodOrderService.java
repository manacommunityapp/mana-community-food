package com.manacommunity.api.food.service;

import com.manacommunity.common.exception.ResourceNotFoundException;
import com.manacommunity.api.food.model.FoodOrder;
import com.manacommunity.api.food.repository.FoodOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodOrderService {

    private final FoodOrderRepository orderRepository;

    public Page<FoodOrder> getUserOrders(Long userId, int page, int size) {
        return orderRepository.findByUserId(userId, PageRequest.of(page, size));
    }

    public List<FoodOrder> getRestaurantOrders(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    public FoodOrder getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Transactional
    public FoodOrder createOrder(Long userId, FoodOrder order) {
        order.setUserId(userId);
        return orderRepository.save(order);
    }

    @Transactional
    public FoodOrder updateOrderStatus(Long id, String status) {
        FoodOrder order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}

