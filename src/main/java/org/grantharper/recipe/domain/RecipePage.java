package org.grantharper.recipe.domain;

import java.util.List;

import org.grantharper.recipe.model.Ingredient;

public class RecipePage
{

  private String title;
  
  private Integer pageNumber;
  
  private String ingredients;
  
  private List<Ingredient> ingredientList;

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public Integer getPageNumber()
  {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber)
  {
    this.pageNumber = pageNumber;
  }

  public String getIngredients()
  {
    return ingredients;
  }

  public void setIngredients(String ingredients)
  {
    this.ingredients = ingredients;
  }
  
  

  public List<Ingredient> getIngredientList()
  {
    return ingredientList;
  }

  public void setIngredientList(List<Ingredient> ingredientList)
  {
    this.ingredientList = ingredientList;
  }

  @Override
  public String toString()
  {
    return "RecipePage [title=" + title + ", pageNumber=" + pageNumber + ", ingredients=" + ingredients + "]";
  }
  
  
  
}
