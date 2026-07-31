package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.FoodRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRecipeRepository extends JpaRepository<FoodRecipe, Long> {
    Page<FoodRecipe> findByCommunityId(Long communityId, Pageable pageable);
    Page<FoodRecipe> findByAuthorId(Long authorId, Pageable pageable);
}
