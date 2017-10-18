package org.grantharper.recipe.validator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RecipeValidatorTest
{
  
  RecipeValidator rv = new RecipeValidator();

  @Test
  public void testTitlePattern()
  {
    assertThat(rv.isValidTitle("green beans"), equalTo(true));
    assertThat(rv.isValidTitle("hazelnut salad (with lots of dressing)"), equalTo(true));
    assertThat(rv.isValidTitle("beans, bread, milk"), equalTo(true));
    
    assertThat(rv.isValidTitle("<>[]{}"), equalTo(false));
    
  }
  
  
  @Test
  public void testPageNumberPattern(){
    assertThat(rv.isValidPageNumber("92"), equalTo(true));
    
    assertThat(rv.isValidPageNumber("a9"), equalTo(false));
  }
  
  
  @Test
  public void testIngredientsPattern(){
    assertThat(rv.isValidIngredients("shallots; onions; apples"), equalTo(true));
    assertThat(rv.isValidIngredients("pork, shoulder of; onions"), equalTo(true));
    assertThat(rv.isValidIngredients("pork (boneless)"), equalTo(true));
    
    assertThat(rv.isValidIngredients("pork < chicken"), equalTo(false));
  }

}
