package org.grantharper.recipe.controller;

import java.util.List;

import javax.validation.Valid;

import org.grantharper.recipe.domain.RecipePage;
import org.grantharper.recipe.domain.RecipeSearch;
import org.grantharper.recipe.model.Ingredient;
import org.grantharper.recipe.model.Recipe;
import org.grantharper.recipe.service.IndexingContract;
import org.grantharper.recipe.validator.RecipeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RecipeController
{
  private static final Logger log = LoggerFactory.getLogger(RecipeController.class);

  @Autowired
  private IndexingContract indexingService;
  
  @Autowired
  private RecipeValidator recipeValidator;
  
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
    model.addAttribute("bookTitles", indexingService.getBookTitles());
    return "recipe-form";
  }

  @RequestMapping(value = "/recipes/add", method = RequestMethod.POST)
  public String postRecipe(Model model, @Valid @ModelAttribute("recipe") RecipePage recipePage, BindingResult bindingResult)
  {
    recipeValidator.validate(recipePage, bindingResult);
    
    if(bindingResult.hasErrors()){
      return "recipe-form";
    }
    
    indexingService.addRecipe(recipePage);
    return "redirect:/recipes";
  }

  @RequestMapping(value = "/recipes", method = RequestMethod.GET)
  public String viewAll(Model model, @PageableDefault(size=20, sort={"pageNumber"}, direction=Direction.ASC) Pageable pageRequest)
  {
    
    Page<Recipe> recipePage = indexingService.viewPagedRecipes(pageRequest);
    PageWrapper<Recipe> page = new PageWrapper<>(recipePage, "/recipes");
    model.addAttribute("recipes", page.getContent());
    model.addAttribute("page", page);
    
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
    model.addAttribute("bookTitles", indexingService.getBookTitles());
    return "recipe-form";
  }
  
  @RequestMapping(value = "/recipes/{recipeId}/edit", method = RequestMethod.POST)
  public String editByRecipeId(Model model, @PathVariable("recipeId") Long recipeId, @ModelAttribute("recipe") RecipePage recipePage , BindingResult bindingResult){
    
    recipeValidator.validate(recipePage, bindingResult);
    
    if(bindingResult.hasErrors()){
      return "recipe-form";
    }
    
    indexingService.updateRecipe(recipeId, recipePage);
    return "redirect:/recipes/" + recipeId;
  }

  @RequestMapping(value = "/ingredients", method = RequestMethod.GET)
  public String viewAllIngredients(Model model, @PageableDefault(size=20, sort={"name"}, direction=Direction.ASC) Pageable pageRequest)
  {
    Page<Ingredient> ingredientPage = indexingService.viewPagedIngredients(pageRequest);
    PageWrapper<Ingredient> page = new PageWrapper<>(ingredientPage, "/ingredients");
    model.addAttribute("ingredients", page.getContent());
    model.addAttribute("page", page);

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
  
  @RequestMapping(value = "/ingredients/search", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<String>> getIngredientSearch(@RequestParam("term") String searchTerm){
    log.debug("SearchTerm=" + searchTerm);
    ResponseEntity<List<String>> result = indexingService.searchIngredients(searchTerm);
    log.debug("SearchResponse=" + result);
    return result;
  }
  

}
