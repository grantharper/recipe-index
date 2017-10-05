package org.grantharper.recipe.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.grantharper.recipe.domain.RecipePage;
import org.grantharper.recipe.model.Ingredient;
import org.grantharper.recipe.model.Recipe;
import org.grantharper.recipe.repository.IngredientRepository;
import org.grantharper.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexingService implements IndexingContract
{

  @Autowired
  RecipeRepository recipeRepo;

  @Autowired
  IngredientRepository ingredientRepo;

  @Override
  public void addRecipe(RecipePage recipePage)
  {
    Recipe recipe = new Recipe();

    recipe.setTitle(recipePage.getTitle());
    recipe.setPageNumber(recipePage.getPageNumber());
    recipe.setIngredients(new HashSet<>());

    // parse ingredients based on commas
    List<String> ingredientNames = Arrays.asList(recipePage.getIngredients().split(","));
    for (String ingredientName : ingredientNames)
    {
      ingredientName = ingredientName.trim().toLowerCase();
      // search for existing ingredients
      Ingredient ingredient = ingredientRepo.findByName(ingredientName);
      
      if(ingredient == null){
        ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);
        ingredient.setRecipes(recipes);
        
      }else{
        ingredient.getRecipes().add(recipe);
      }
      
      recipe.getIngredients().add(ingredient);
    }
    
    recipeRepo.save(recipe);

  }

  @Override
  public List<Recipe> viewRecipes()
  {
    List<Recipe> recipes = recipeRepo.findAllOrderByPageNumber();
//    List<RecipePage> recipePages = new ArrayList<>();
//    for(Recipe recipe: recipes){
//      RecipePage recipePage = new RecipePage();
//      recipePage.setTitle(recipe.getTitle());
//      recipePage.setPageNumber(recipe.getPageNumber());
//      String ingredients = "";
//      for(Ingredient ingredient : recipe.getIngredients()){
//        ingredients = ingredients + ingredient.getName() + ", ";
//      }
//      recipePage.setIngredients(ingredients);
//      recipePages.add(recipePage);
//    }
    
    return recipes;
  }

  @Override
  public List<Ingredient> viewIngredients()
  {
    return ingredientRepo.findAllOrderByName();
  }

  @Override
  public Ingredient viewIngredientById(Long ingredientId)
  {
    return ingredientRepo.findOne(ingredientId);
  }

  public Recipe viewRecipeById(Long recipeId)
  {
    return recipeRepo.findOne(recipeId);
  }

  public void deleteRecipeById(Long recipeId)
  {
    Recipe recipe = recipeRepo.findOne(recipeId);
    
    recipe.getIngredients().clear();
    
    recipeRepo.delete(recipe);
    
  }

  @Override
  public void deleteIngredientById(Long ingredientId)
  {
    Ingredient ingredient = ingredientRepo.findOne(ingredientId);
    
    //check if the ingredient is still in a recipe
    if(!ingredient.getRecipes().isEmpty()){
      return;
    }
    
    ingredient.getRecipes().clear();
    
    ingredientRepo.delete(ingredient);
    
  }

  @Override
  public List<Recipe> searchRecipesByTitle(String recipeTitle)
  {
    List<Recipe> recipeResults = recipeRepo.findByTitleContains(recipeTitle);
    return recipeResults;
  }

  @Override
  public List<Recipe> searchRecipes(String searchTerm)
  {
    List<Recipe> recipeResults = recipeRepo.findDistinctRecipeByIngredientsNameContainsOrTitleContains(searchTerm, searchTerm);
    
    return recipeResults;
  }
  
  

}
