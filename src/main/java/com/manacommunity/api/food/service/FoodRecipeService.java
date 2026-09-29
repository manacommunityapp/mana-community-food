package com.manacommunity.api.food.service;

import com.manacommunity.common.exception.ResourceNotFoundException;
import com.manacommunity.api.food.model.FoodRecipe;
import com.manacommunity.api.food.model.RecipeComment;
import com.manacommunity.api.food.repository.FoodRecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodRecipeService {

    private final FoodRecipeRepository recipeRepository;

    public Page<FoodRecipe> getRecipes(int page, int size, Long communityId) {
        PageRequest pageable = PageRequest.of(page, size);
        if (communityId != null) {
            return recipeRepository.findByCommunityId(communityId, pageable);
        }
        return recipeRepository.findAll(pageable);
    }

    public FoodRecipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));
    }

    @Transactional
    public FoodRecipe createRecipe(Long authorId, FoodRecipe recipe) {
        recipe.setAuthorId(authorId);
        return recipeRepository.save(recipe);
    }

    @Transactional
    public RecipeComment addComment(Long recipeId, Long userId, String text) {
        FoodRecipe recipe = getRecipeById(recipeId);
        RecipeComment comment = RecipeComment.builder()
                .userId(userId)
                .text(text)
                .build();
        recipe.getComments().add(comment);
        recipeRepository.save(recipe);
        return comment;
    }
}

