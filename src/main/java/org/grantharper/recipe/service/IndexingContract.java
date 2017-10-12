package org.grantharper.recipe.service;

import java.util.List;

import org.grantharper.recipe.domain.RecipePage;
import org.grantharper.recipe.model.Ingredient;
import org.grantharper.recipe.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IndexingContract
{

  public void addRecipe(RecipePage recipePage);
  
  public List<Recipe> viewRecipes();
  
  public Page<Recipe> viewPagedRecipes(Pageable pageable);
  
  public List<Ingredient> viewIngredients();
  
  public Ingredient viewIngredientById(Long id);
  
  public Recipe viewRecipeById(Long recipeId);
  
  public RecipePage editViewRecipeById(Long recipeId);
  
  public void updateRecipe(Long recipeId, RecipePage recipePage);
  
  public void deleteRecipeById(Long recipeId);
  
  public void deleteIngredientById(Long ingredientId);
  
  public List<Recipe> searchRecipesByTitle(String recipeTitle);
  
  public List<Recipe> searchRecipes(String ingredientName);
}
