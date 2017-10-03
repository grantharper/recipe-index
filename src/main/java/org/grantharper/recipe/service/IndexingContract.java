package org.grantharper.recipe.service;

import java.util.List;

import org.grantharper.recipe.domain.RecipePage;
import org.grantharper.recipe.model.Ingredient;
import org.grantharper.recipe.model.Recipe;

public interface IndexingContract
{

  public void addRecipe(RecipePage recipePage);
  
  public List<Recipe> viewRecipes();
  
  public List<Ingredient> viewIngredients();
  
  public Ingredient viewIngredientById(Long id);
  
  public Recipe viewRecipeById(Long recipeId);
  
  public void deleteRecipeById(Long recipeId);
  
  public void deleteIngredientById(Long ingredientId);
}
