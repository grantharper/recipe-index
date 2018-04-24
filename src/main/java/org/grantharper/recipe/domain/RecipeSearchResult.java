package org.grantharper.recipe.domain;

import java.util.List;

public class RecipeSearchResult
{

  private String title;
  
  private String book;

  private String pageNumber;

  private List<String> ingredients;

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getBook()
  {
    return book;
  }

  public void setBook(String book)
  {
    this.book = book;
  }

  public String getPageNumber()
  {
    return pageNumber;
  }

  public void setPageNumber(String pageNumber)
  {
    this.pageNumber = pageNumber;
  }

  public List<String> getIngredients()
  {
    return ingredients;
  }

  public void setIngredients(List<String> ingredients)
  {
    this.ingredients = ingredients;
  }
}
