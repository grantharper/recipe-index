package org.grantharper.recipe.validator;

import java.util.regex.Pattern;

import org.grantharper.recipe.domain.RecipePage;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RecipeValidator implements Validator
{

    private Pattern titlePattern = Pattern.compile("^[A-Za-z0-9-\\'\\s\\,\\(\\)]*$");

    private Pattern pageNumberPattern = Pattern.compile("^[0-9]*$");

    private Pattern ingredientsPattern = Pattern.compile("^[A-Za-z0-9-\\'\\s\\,\\;\\(\\)]*$");

    boolean isValidTitle(String input)
    {
        return titlePattern.matcher(input).matches();
    }

    boolean isValidPageNumber(String input)
    {
        return pageNumberPattern.matcher(input).matches();
    }

    boolean isValidIngredients(String input)
    {
        return ingredientsPattern.matcher(input).matches();
    }

    @Override
    public boolean supports(Class<?> clazz)
    {
        return RecipePage.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors e)
    {
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "title", "title.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "pageNumber", "pageNumber.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "ingredients", "ingredients.required");

        RecipePage rp = (RecipePage) o;

        if (!isValidTitle(rp.getTitle()))
        {
            e.rejectValue("title", "title.invalid");
        }

        if (!pageNumberPattern.matcher(rp.getPageNumber()).matches())
        {
            e.rejectValue("pageNumber", "pageNumber.invalid");
        }

        if (!ingredientsPattern.matcher(rp.getIngredients()).matches())
        {
            e.rejectValue("ingredients", "ingredients.invalid");
        }

    }

}
