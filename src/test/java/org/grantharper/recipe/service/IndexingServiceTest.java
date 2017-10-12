package org.grantharper.recipe.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.TreeSet;

import org.grantharper.recipe.domain.RecipePage;
import org.grantharper.recipe.model.Ingredient;
import org.grantharper.recipe.model.Recipe;
import org.grantharper.recipe.repository.IngredientRepository;
import org.grantharper.recipe.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IndexingServiceTest
{
  
  @InjectMocks
  private IndexingService indexingService;
  
  @Mock
  private RecipeRepository recipeRepo;
  
  @Mock
  private IngredientRepository ingredientRepo;
  
  
  private static final String INGREDIENT_1 = "asparagus";
  private static final String INGREDIENT_2 = "pepper";
  private static final String INGREDIENT_3 = "shallot";
  private static final String INGREDIENT_LIST = INGREDIENT_1 + ", " + INGREDIENT_2 + ", " + INGREDIENT_3;
  private static final String RECIPE_TITLE = "asparagus and shallot fun";
  private static final String RECIPE_PAGE_NUMBER_STRING = "1";
  private static final Integer RECIPE_PAGE_NUMBER_INT = Integer.valueOf(RECIPE_PAGE_NUMBER_STRING);
  private static final Long RECIPE_ID = 1L;
  private static final Ingredient DB_INGREDIENT_1 = new Ingredient();
  private static final Ingredient DB_INGREDIENT_2 = new Ingredient();
  private static final Ingredient DB_INGREDIENT_3 = new Ingredient();
  private static final Recipe DB_RECIPE_1 = new Recipe();
  private static final Set<Ingredient> DB_INGREDIENTS = new TreeSet<>();
  
  @Before
  public void initMocks(){
    MockitoAnnotations.initMocks(this);
    DB_INGREDIENT_1.setId(1L);
    DB_INGREDIENT_1.setName(INGREDIENT_1);
    DB_INGREDIENT_2.setId(2L);
    DB_INGREDIENT_2.setName(INGREDIENT_2);
    DB_INGREDIENT_3.setId(3L);
    DB_INGREDIENT_3.setName(INGREDIENT_3);
    DB_RECIPE_1.setTitle(RECIPE_TITLE);
    DB_RECIPE_1.setPageNumber(RECIPE_PAGE_NUMBER_INT);
    DB_INGREDIENTS.add(DB_INGREDIENT_1);
    DB_INGREDIENTS.add(DB_INGREDIENT_2);
    DB_INGREDIENTS.add(DB_INGREDIENT_3);

    DB_RECIPE_1.setIngredients(DB_INGREDIENTS);
  }
  
  @Test
  public void testParseIngredients(){
    RecipePage recipePage = new RecipePage();
    recipePage.setIngredients(INGREDIENT_LIST);
    Recipe recipe = new Recipe();
    recipe.setTitle(RECIPE_TITLE);
    recipe.setPageNumber(RECIPE_PAGE_NUMBER_INT);
    when(ingredientRepo.findByName(INGREDIENT_1)).thenReturn(DB_INGREDIENT_1);
    indexingService.parseIngredients(recipePage, recipe);
    
    assertThat(recipe.getIngredients().size(), equalTo(3));
    assertThat(recipe.getIngredients().contains(DB_INGREDIENT_1), equalTo(true));
  }

  @Test
  public void testEditViewRecipeById()
  {
    when(recipeRepo.findOne(RECIPE_ID)).thenReturn(DB_RECIPE_1);
    RecipePage recipePage = indexingService.editViewRecipeById(RECIPE_ID);
    
    assertThat(recipePage.getTitle(), equalTo(RECIPE_TITLE));
    assertThat(recipePage.getPageNumber(), equalTo(RECIPE_PAGE_NUMBER_STRING));
    assertThat(recipePage.getIngredients(), equalTo(INGREDIENT_LIST));
    
  }

}
