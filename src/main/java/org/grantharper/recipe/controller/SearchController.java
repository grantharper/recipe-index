package org.grantharper.recipe.controller;

import org.grantharper.recipe.domain.RecipeSearch;
import org.grantharper.recipe.domain.RecipeSearchResult;
import org.grantharper.recipe.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value="/elasticsearch")
public class SearchController
{

  @Autowired
  SearchService searchService;

  @RequestMapping(method = RequestMethod.GET)
  public String getIndex(Model model)
  {
    model.addAttribute("searchRecipe", new RecipeSearch());
    return "elasticsearch";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String search(Model model, @ModelAttribute("searchRecipe") RecipeSearch recipeSearch)
  {
    List<RecipeSearchResult> recipePageList = searchService.searchElasticsearchForIngredients(recipeSearch.getSearchTerm());
    model.addAttribute("searchResults", recipePageList);
    return "elasticsearch";
  }
}
