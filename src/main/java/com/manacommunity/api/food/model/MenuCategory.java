package com.manacommunity.api.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food_menu_categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(name = "display_order")
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private FoodRestaurant restaurant;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MenuItem> items = new ArrayList<>();
}
