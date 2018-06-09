package org.grantharper.recipe.domain;

public class FoodLogIntakePage
{

  private String ingredientName;

  private String amountValue;

  private String amountUnit;

  public String getIngredientName()
  {
    return ingredientName;
  }

  public void setIngredientName(String ingredientName)
  {
    this.ingredientName = ingredientName;
  }

  public String getAmountValue()
  {
    return amountValue;
  }

  public void setAmountValue(String amountValue)
  {
    this.amountValue = amountValue;
  }

  public String getAmountUnit()
  {
    return amountUnit;
  }

  public void setAmountUnit(String amountUnit)
  {
    this.amountUnit = amountUnit;
  }

  @Override
  public String toString()
  {
    return "FoodLogIntakePage{" +
            "ingredientName='" + ingredientName + '\'' +
            ", amountValue='" + amountValue + '\'' +
            ", amountUnit='" + amountUnit + '\'' +
            '}';
  }
}
