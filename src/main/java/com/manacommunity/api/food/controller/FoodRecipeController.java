package com.manacommunity.api.food.controller;

import com.manacommunity.api.food.model.FoodRecipe;
import com.manacommunity.api.food.model.RecipeComment;
import com.manacommunity.api.food.service.FoodRecipeService;
import com.manacommunity.common.user.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/food/recipes")
@RequiredArgsConstructor
public class FoodRecipeController {

    private final FoodRecipeService recipeService;

    @GetMapping
    public ResponseEntity<Page<FoodRecipe>> getRecipes(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Long communityId = principal != null ? principal.getCommunityId() : null;
        return ResponseEntity.ok(recipeService.getRecipes(page, size, communityId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodRecipe> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @PostMapping
    public ResponseEntity<FoodRecipe> createRecipe(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody FoodRecipe recipe) {
        return ResponseEntity.ok(recipeService.createRecipe(principal.getId(), recipe));
    }

    @PostMapping("/{recipeId}/comments")
    public ResponseEntity<RecipeComment> addComment(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long recipeId,
            @RequestBody Map<String, String> body) {
        String text = body.get("text");
        return ResponseEntity.ok(recipeService.addComment(recipeId, principal.getId(), text));
    }
}

