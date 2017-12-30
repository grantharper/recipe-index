package org.grantharper.recipe.bootstrap;

import org.grantharper.recipe.domain.RecipePage;
import org.grantharper.recipe.model.Book;
import org.grantharper.recipe.repository.RecipeRepository;
import org.grantharper.recipe.service.IndexingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile("none")
public class LoadDevRecipes implements ApplicationListener<ContextRefreshedEvent>
{

    @Autowired
    RecipeRepository recipeRepo;

    @Autowired
    IndexingService indexingService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {

        loadRecipes();

    }

    private static final String RECIPE_1_TITLE = "asparagus and shallot fun";
    private static final String RECIPE_1_PAGE_NUMBER = "72";
    private static final String RECIPE_2_TITLE = "roasted cauliflower";
    private static final String RECIPE_2_PAGE_NUMBER = "50";
    private static final String RECIPE_3_TITLE = "beet endive salad";
    private static final String RECIPE_3_PAGE_NUMBER = "1";

    private static final String INGREDIENT_1 = "asparagus";
    private static final String INGREDIENT_2 = "pepper";
    private static final String INGREDIENT_3 = "shallot";
    private static final String INGREDIENT_4 = "cauliflower";
    private static final String INGREDIENT_5 = "capers";
    private static final String INGREDIENT_6 = "pine nut";
    private static final String INGREDIENT_7 = "beet";
    private static final String INGREDIENT_8 = "endive";
    private static final String INGREDIENT_9 = "black olive";
    private static final String INGREDIENT_10 = "orange";

    private static final String INGREDIENT_LIST_1 = INGREDIENT_1 + "; " + INGREDIENT_2 + "; " + INGREDIENT_3;
    private static final String INGREDIENT_LIST_2 = INGREDIENT_4 + "; " + INGREDIENT_5 + "; " + INGREDIENT_6;
    private static final String INGREDIENT_LIST_3 = INGREDIENT_7 + "; " + INGREDIENT_8 + "; " + INGREDIENT_9 + "; "
            + INGREDIENT_10;

    private static final String BOOK_TITLE = "slt";
    private static final String BOOK_AUTHOR = "various";
    
    public void loadRecipes()
    {
        Book book = new Book();
        book.setTitle(BOOK_TITLE);
        book.setAuthor(BOOK_AUTHOR);
        
        RecipePage recipe1 = new RecipePage();
        recipe1.setTitle(RECIPE_1_TITLE);
        recipe1.setPageNumber(RECIPE_1_PAGE_NUMBER);
        recipe1.setIngredients(INGREDIENT_LIST_1);
        indexingService.addRecipe(recipe1);

        RecipePage recipe2 = new RecipePage();
        recipe2.setTitle(RECIPE_2_TITLE);
        recipe2.setPageNumber(RECIPE_2_PAGE_NUMBER);
        recipe2.setIngredients(INGREDIENT_LIST_2);
        indexingService.addRecipe(recipe2);

        RecipePage recipe3 = new RecipePage();
        recipe3.setTitle(RECIPE_3_TITLE);
        recipe3.setPageNumber(RECIPE_3_PAGE_NUMBER);
        recipe3.setIngredients(INGREDIENT_LIST_3);
        indexingService.addRecipe(recipe3);

    }

}
