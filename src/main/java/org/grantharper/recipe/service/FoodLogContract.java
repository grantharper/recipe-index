package org.grantharper.recipe.service;

import org.grantharper.recipe.domain.FoodLogConsumptionPage;
import org.grantharper.recipe.domain.FoodLogIntakePage;

public interface FoodLogContract
{

  void saveConsumption(FoodLogConsumptionPage foodLogConsumptionPage);

  void saveIntake(FoodLogIntakePage foodLogIntakePage);

}
