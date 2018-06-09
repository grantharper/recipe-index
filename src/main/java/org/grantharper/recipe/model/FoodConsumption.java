package org.grantharper.recipe.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class FoodConsumption
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "consumption_id")
  private Long id;

  private String recipeTitle;

  private String recipeUri;

  private LocalDate date;

  private String meal;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getRecipeTitle()
  {
    return recipeTitle;
  }

  public void setRecipeTitle(String recipeTitle)
  {
    this.recipeTitle = recipeTitle;
  }

  public String getRecipeUri()
  {
    return recipeUri;
  }

  public void setRecipeUri(String recipeUri)
  {
    this.recipeUri = recipeUri;
  }

  public LocalDate getDate()
  {
    return date;
  }

  public void setDate(LocalDate date)
  {
    this.date = date;
  }

  public String getMeal()
  {
    return meal;
  }

  public void setMeal(String meal)
  {
    this.meal = meal;
  }
}
