package org.grantharper.recipe.service;

import org.grantharper.recipe.domain.FoodLogConsumptionPage;
import org.grantharper.recipe.domain.FoodLogIntakePage;
import org.grantharper.recipe.model.FoodConsumption;
import org.grantharper.recipe.model.Ingredient;
import org.grantharper.recipe.model.IngredientIntake;
import org.grantharper.recipe.model.MeasurementUnit;
import org.grantharper.recipe.repository.FoodConsumptionRepository;
import org.grantharper.recipe.repository.IngredientIntakeRepository;
import org.grantharper.recipe.repository.IngredientRepository;
import org.grantharper.recipe.repository.MeasurementUnitRepository;
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
  private FoodConsumptionRepository foodConsumptionRepo;

  @Autowired
  private IngredientRepository ingredientRepo;

  @Autowired
  private MeasurementUnitRepository measurementUnitRepo;

  @Autowired
  private IngredientIntakeRepository ingredientIntakeRepo;

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

    foodConsumptionRepo.save(foodConsumption);

  }

  @Override
  public void saveIntake(FoodLogIntakePage foodLogIntakePage)
  {
    log.debug(foodLogIntakePage.toString());
    IngredientIntake ingredientIntake = new IngredientIntake();

    String ingredientName = foodLogIntakePage.getIngredientName().trim()
            .toLowerCase();
    // search for existing ingredients
    Ingredient ingredient = ingredientRepo.findByName(ingredientName);

    if (ingredient == null) {
      ingredient = new Ingredient();

      ingredient.setName(ingredientName);
    }

    ingredientIntake.setIngredient(ingredient);

    ingredientIntake.setAmountValue(Double.valueOf(foodLogIntakePage.getAmountValue()));

    String measurementUnitName = foodLogIntakePage.getAmountUnit();

    MeasurementUnit measurementUnit = measurementUnitRepo.findByUnitText(measurementUnitName);

    if (measurementUnit == null) {
      measurementUnit = new MeasurementUnit();
      measurementUnit.setUnitText(measurementUnitName);
    }

    ingredientIntake.setMeasurementUnit(measurementUnit);

    ingredientIntakeRepo.save(ingredientIntake);
  }
}
