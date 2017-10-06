package org.grantharper.recipe.controller;

import org.grantharper.recipe.domain.RecipePage;
import org.grantharper.recipe.domain.RecipeSearch;
import org.grantharper.recipe.service.IndexingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RecipeController
{
  private static final Logger log = LoggerFactory.getLogger(RecipeController.class);

  @Autowired
  IndexingService indexingService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String getIndex(Model model)
  {
    model.addAttribute("searchRecipe", new RecipeSearch());
    return "index";
  }
  
  @RequestMapping(value = "/recipes/search", method = RequestMethod.POST)
  public String searchRecipes(Model model, @ModelAttribute("searchRecipe") RecipeSearch recipeSearch){
    model.addAttribute("recipeResults", indexingService.searchRecipes(recipeSearch.getSearchTerm()));
    return "index";
  }

  @RequestMapping(value = "/recipes/add")
  public String getRecipeAdd(Model model)
  {
    model.addAttribute("recipe", new RecipePage());
    return "recipe-form";
  }

  @RequestMapping(value = "/recipes/add", method = RequestMethod.POST)
  public String postRecipe(Model model, @ModelAttribute("recipe") RecipePage recipePage)
  {
    indexingService.addRecipe(recipePage);
    return "redirect:/recipes";
  }

  @RequestMapping(value = "/recipes", method = RequestMethod.GET)
  public String viewAll(Model model)
  {
    model.addAttribute("recipes", indexingService.viewRecipes());
    return "all-recipes";
  }

  @RequestMapping(value = "/recipes/{recipeId}", method = RequestMethod.GET)
  public String viewByRecipeId(Model model, @PathVariable("recipeId") Long recipeId)
  {
    model.addAttribute("recipe", indexingService.viewRecipeById(recipeId));
    return "recipe";
  }
  
  @RequestMapping(value = "/recipes/{recipeId}/delete", method = RequestMethod.POST)
  public String deleteByRecipeId(Model model, @PathVariable("recipeId") Long recipeId)
  {
    indexingService.deleteRecipeById(recipeId);
    return "redirect:/recipes";
  }
  
  @RequestMapping(value = "/recipes/{recipeId}/edit", method = RequestMethod.GET)
  public String editViewByRecipeId(Model model, @PathVariable("recipeId") Long recipeId){
    model.addAttribute("recipe", indexingService.editViewRecipeById(recipeId));
    return "recipe-form";
  }
  
  @RequestMapping(value = "/recipes/{recipeId}/edit", method = RequestMethod.POST)
  public String editByRecipeId(Model model, @PathVariable("recipeId") Long recipeId, @ModelAttribute("recipe") RecipePage recipePage){
    indexingService.updateRecipe(recipeId, recipePage);
    return "redirect:/recipes/" + recipeId;
  }

  @RequestMapping(value = "/ingredients", method = RequestMethod.GET)
  public String viewAllIngredients(Model model)
  {
    model.addAttribute("ingredients", indexingService.viewIngredients());
    return "all-ingredients";
  }

  @RequestMapping(value = "/ingredients/{ingredientId}", method = RequestMethod.GET)
  public String viewByIngredient(Model model, @PathVariable("ingredientId") Long ingredientId)
  {
    model.addAttribute("ingredient", indexingService.viewIngredientById(ingredientId));
    return "ingredient";
  }
  
  @RequestMapping(value = "/ingredients/{ingredientId}/delete", method = RequestMethod.POST)
  public String deleteByIngredientId(Model model, @PathVariable("ingredientId") Long ingredientId)
  {
    indexingService.deleteIngredientById(ingredientId);
    return "redirect:/ingredients";
  }
  

}
