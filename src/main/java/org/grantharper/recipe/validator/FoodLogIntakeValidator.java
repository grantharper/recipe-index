package org.grantharper.recipe.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FoodLogIntakeValidator implements Validator
{

  @Override
  public boolean supports(Class<?> clazz)
  {
    return false;
  }

  @Override
  public void validate(Object target, Errors errors)
  {

  }
}