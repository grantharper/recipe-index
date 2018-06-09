package org.grantharper.recipe.service;

import org.grantharper.recipe.domain.FoodLogConsumptionPage;
import org.grantharper.recipe.domain.FoodLogIntakePage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FoodLogContract
{

  void saveConsumption(FoodLogConsumptionPage foodLogConsumptionPage);

  void saveIntake(FoodLogIntakePage foodLogIntakePage);

  ResponseEntity<List<String>> getMeasurementUnits(String searchTerm);
}
