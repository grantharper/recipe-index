package org.grantharper.recipe.domain;

public class FoodLogConsumptionPage
{

  private String recipeTitle;

  private String recipeUri;

  private String date;

  private Meal meal;

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

  public String getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    this.date = date;
  }

  public Meal getMeal()
  {
    return meal;
  }

  public void setMeal(Meal meal)
  {
    this.meal = meal;
  }

  @Override
  public String toString()
  {
    return "FoodLogConsumptionPage{" +
            "recipeTitle='" + recipeTitle + '\'' +
            ", recipeUri='" + recipeUri + '\'' +
            ", date='" + date + '\'' +
            ", meal=" + meal +
            '}';
  }
}
