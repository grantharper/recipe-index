package org.grantharper.recipe.domain;

import java.util.ArrayList;
import java.util.List;

import org.grantharper.recipe.model.Ingredient;

public class IngredientSearchResults
{
  private List<String> searchResults;

  public IngredientSearchResults(List<Ingredient> ingredients)
  {
    searchResults = new ArrayList<>();
    for(Ingredient ingredient: ingredients){
      searchResults.add(ingredient.getName());
    }
  }

  public List<String> getSearchResults()
  {
    return searchResults;
  }

  public void setSearchResults(List<String> searchResults)
  {
    this.searchResults = searchResults;
  }

  @Override
  public String toString()
  {
    return "IngredientSearchResults [searchResults=" + searchResults + "]";
  }

  
  
  
}
