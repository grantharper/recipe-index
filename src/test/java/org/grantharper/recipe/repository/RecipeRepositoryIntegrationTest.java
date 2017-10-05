package org.grantharper.recipe.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.grantharper.recipe.model.Ingredient;
import org.grantharper.recipe.model.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecipeRepositoryIntegrationTest
{

  @Autowired
  private RecipeRepository recipeRepo;

  private static final String RECIPE_TITLE = "fake recipe";
  private static final Integer RECIPE_PAGE_NUMBER = 1;
  private static final String INGREDIENT_NAME = "sherry vinegar";

  @Before
  public void setUp()
  {
    Recipe recipe = new Recipe();
    recipe.setTitle(RECIPE_TITLE);
    recipe.setPageNumber(1);
    Ingredient ingredient = new Ingredient();
    ingredient.setName(INGREDIENT_NAME);
    Set<Recipe> recipes = new HashSet<>();
    recipes.add(recipe);
    Set<Ingredient> ingredients = new HashSet<>();
    ingredients.add(ingredient);
    recipe.setIngredients(ingredients);
    ingredient.setRecipes(recipes);

    Recipe savedRecipe = recipeRepo.save(recipe);
    System.out.println(savedRecipe.toString());
  }

  @Test
  public void testFindAll()
  {
    List<Recipe> recipes = recipeRepo.findAll();

    assertThat(recipes.size(), equalTo(1));

  }

  @Test
  public void testSearch()
  {

    List<Recipe> recipes = recipeRepo.findDistinctRecipeByIngredientsNameContainsOrTitleContains(RECIPE_TITLE, RECIPE_TITLE);
    assertThat(recipes.size(), equalTo(1));
    
    recipes = recipeRepo.findDistinctRecipeByIngredientsNameContainsOrTitleContains(INGREDIENT_NAME, INGREDIENT_NAME);
    assertThat(recipes.size(), equalTo(1));
    
    recipes = recipeRepo.findDistinctRecipeByIngredientsNameContainsOrTitleContains(INGREDIENT_NAME.substring(3, 6), INGREDIENT_NAME.substring(3, 6));
    assertThat(recipes.size(), equalTo(1));
    
    recipes = recipeRepo.findDistinctRecipeByIngredientsNameContainsOrTitleContains(RECIPE_TITLE.substring(2,  7), RECIPE_TITLE.substring(2,  7));
    assertThat(recipes.size(), equalTo(1));
  }

}
