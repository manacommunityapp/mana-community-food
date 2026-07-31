package com.manacommunity.api.food.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "food_order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_item_id", nullable = false)
    private Long menuItemId;

    @Column(name = "item_name", nullable = false, length = 150)
    private String itemName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
