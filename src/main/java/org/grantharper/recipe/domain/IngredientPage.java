package org.grantharper.recipe.domain;

import java.util.List;

import org.grantharper.recipe.model.Recipe;

public class IngredientPage
{

    private String name;

    private String recipes;

    private List<Recipe> recipeList;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRecipes()
    {
        return recipes;
    }

    public void setRecipes(String recipes)
    {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipeList()
    {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList)
    {
        this.recipeList = recipeList;
    }

}
