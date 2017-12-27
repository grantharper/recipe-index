package org.grantharper.recipe.domain;

import org.springframework.util.StringUtils;

public class RecipePage
{

    private String title;

    private String pageNumber;

    private String ingredients;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        /*
         * if (StringUtils.isEmpty(title)) throw new
         * IllegalArgumentException("cannot have blank title");
         */

        this.title = title.toLowerCase();
    }

    public String getPageNumber()
    {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    public String getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(String ingredients)
    {
        /*
         * if (StringUtils.isEmpty(ingredients)) throw new
         * IllegalArgumentException("cannot have blank ingredients");
         */

        this.ingredients = ingredients.toLowerCase();

    }

    @Override
    public String toString()
    {
        return "RecipePage [title=" + title + ", pageNumber=" + pageNumber + ", ingredients=" + ingredients + "]";
    }

}
