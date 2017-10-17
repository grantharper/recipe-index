package org.grantharper.recipe.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.grantharper.recipe.domain.RecipePage;
import org.grantharper.recipe.model.Ingredient;
import org.grantharper.recipe.model.Recipe;
import org.grantharper.recipe.repository.IngredientRepository;
import org.grantharper.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    recipe.setPageNumber(Integer.valueOf(recipePage.getPageNumber()));
    recipe.setIngredients(new HashSet<>());

    parseIngredients(recipePage, recipe);
    
    recipeRepo.save(recipe);

  }

  void parseIngredients(RecipePage recipePage, Recipe recipe)
  {
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
  }

  @Override
  public List<Recipe> viewRecipes()
  {
    List<Recipe> recipes = recipeRepo.findAllOrderByPageNumber();
    
    return recipes;
  }
  
  @Override
  public RecipePage editViewRecipeById(Long recipeId)
  {
    Recipe recipe = recipeRepo.findOne(recipeId);
    RecipePage recipePage = new RecipePage();
    
    recipePage.setTitle(recipe.getTitle());
    recipePage.setPageNumber(recipe.getPageNumber().toString());
    
    String ingredients = "";
    Iterator<Ingredient> i = recipe.getIngredients().iterator();
    while(i.hasNext())
    {
      Ingredient ingredient = i.next();
      if(i.hasNext()){
        ingredients += ingredient.getName() + ", ";
      }else{
        ingredients += ingredient.getName();
      }
    }
    recipePage.setIngredients(ingredients);
    
    return recipePage;
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
    List<Recipe> recipeResults = recipeRepo.findDistinctRecipeByIngredientsNameContainsOrTitleContainsOrderByPageNumber(searchTerm, searchTerm);
    
    return recipeResults;
  }

  @Override
  public void updateRecipe(Long recipeId, RecipePage recipePage)
  {
    Recipe recipe = recipeRepo.findOne(recipeId);
    
    recipe.setTitle(recipePage.getTitle());
    recipe.setPageNumber(Integer.valueOf(recipePage.getPageNumber()));
    recipe.getIngredients().clear();
    parseIngredients(recipePage, recipe);
    
    recipeRepo.save(recipe);
    
  }

  @Override
  public Page<Recipe> viewPagedRecipes(Pageable pageable)
  {
    Page<Recipe> pagedRecipes = recipeRepo.findAll(pageable);
    
    return pagedRecipes;
  }

  @Override
  public ResponseEntity<List<String>> searchIngredients(String term)
  {
    List<Ingredient> ingredients = ingredientRepo.findTop5ByNameContainsOrderByNameAsc(term);
    
    List<String> ingredientNames = new ArrayList<>();
    Stream<Ingredient> fromList = ingredients.stream();
    fromList.forEach((a) -> {
      ingredientNames.add(a.getName());
    });
    
    return new ResponseEntity<List<String>>(ingredientNames, HttpStatus.OK);

  }
  

}
