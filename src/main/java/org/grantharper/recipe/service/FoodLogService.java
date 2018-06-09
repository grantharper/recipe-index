package org.grantharper.recipe.service;

import org.grantharper.recipe.controller.FoodLogController;
import org.grantharper.recipe.domain.FoodLogConsumptionPage;
import org.grantharper.recipe.domain.FoodLogIntakePage;
import org.grantharper.recipe.model.FoodConsumption;
import org.grantharper.recipe.repository.FoodConsumptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class FoodLogService implements FoodLogContract
{
  private static final Logger log = LoggerFactory.getLogger(FoodLogService.class);

  @Autowired
  private FoodConsumptionRepository foodConsumptionRepository;

  @Override
  public void saveConsumption(FoodLogConsumptionPage foodLogConsumptionPage)
  {
    log.debug(foodLogConsumptionPage.toString());

    FoodConsumption foodConsumption = new FoodConsumption();

    foodConsumption.setRecipeTitle(foodLogConsumptionPage.getRecipeTitle());
    foodConsumption.setRecipeUri(foodLogConsumptionPage.getRecipeUri());
    LocalDate date = LocalDate.parse(foodLogConsumptionPage.getDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));
    foodConsumption.setDate(date);
    foodConsumption.setMeal(foodLogConsumptionPage.getMeal().toString());

    foodConsumptionRepository.save(foodConsumption);

  }

  @Override
  public void saveIntake(FoodLogIntakePage foodLogIntakePage)
  {

  }
}
