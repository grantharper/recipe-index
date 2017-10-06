package org.grantharper.recipe.domain;

import java.util.List;

import org.grantharper.recipe.model.Ingredient;
import org.springframework.util.StringUtils;

public class RecipePage
{

  private String title;

  private Integer pageNumber;

  private String ingredients;

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    if (StringUtils.isEmpty(title))
      throw new IllegalArgumentException("cannot have blank title");
    
    this.title = title.toLowerCase();
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
    if (StringUtils.isEmpty(ingredients))
      throw new IllegalArgumentException("cannot have blank ingredients");

    this.ingredients = ingredients.toLowerCase();

  }

  @Override
  public String toString()
  {
    return "RecipePage [title=" + title + ", pageNumber=" + pageNumber + ", ingredients=" + ingredients + "]";
  }

}
