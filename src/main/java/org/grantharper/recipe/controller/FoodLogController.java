package org.grantharper.recipe.controller;

import org.grantharper.recipe.domain.FoodLogConsumptionPage;
import org.grantharper.recipe.domain.FoodLogIntakePage;
import org.grantharper.recipe.domain.Meal;
import org.grantharper.recipe.service.FoodLogContract;
import org.grantharper.recipe.validator.FoodLogConsumptionValidator;
import org.grantharper.recipe.validator.FoodLogIntakeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value="/food-log")
public class FoodLogController
{
  private static final Logger log = LoggerFactory.getLogger(FoodLogController.class);
  private static final String CONSUMPTION_MODEL_ATTRIBUTE = "consumption";
  private static final String INTAKE_MODEL_ATTRIBUTE = "intake";
  private static final String INTAKE_FORM = "intake-form";
  private static final String CONSUMPTION_FORM = "consumption-form";

  @Autowired
  private FoodLogContract foodLogContract;
  
  @Autowired
  private FoodLogConsumptionValidator foodLogConsumptionValidator;

  @Autowired
  private FoodLogIntakeValidator foodLogIntakeValidator;

  @RequestMapping(value = "/consumption", method = RequestMethod.GET)
  public String getConsumptionLog(Model model)
  {
    model.addAttribute(CONSUMPTION_MODEL_ATTRIBUTE, new FoodLogConsumptionPage());
    model.addAttribute("meals", Meal.values());
    return CONSUMPTION_FORM;
  }

  @RequestMapping(value = "/consumption", method = RequestMethod.POST)
  public String postConsumptionLog(Model model, @Valid @ModelAttribute(CONSUMPTION_MODEL_ATTRIBUTE) FoodLogConsumptionPage foodLogConsumptionPage, BindingResult bindingResult)
  {
    foodLogConsumptionValidator.validate(foodLogConsumptionPage, bindingResult);
    
    if(bindingResult.hasErrors()){
      return CONSUMPTION_FORM;
    }
    
    foodLogContract.saveConsumption(foodLogConsumptionPage);
    return "redirect:/food-log/consumption";
  }

  @RequestMapping(value = "/intake", method = RequestMethod.GET)
  public String getIntakeLog(Model model)
  {
    model.addAttribute(INTAKE_MODEL_ATTRIBUTE, new FoodLogIntakePage());
    return INTAKE_FORM;
  }

  @RequestMapping(value = "/intake", method = RequestMethod.POST)
  public String postIntakeLog(Model model, @Valid @ModelAttribute(INTAKE_MODEL_ATTRIBUTE) FoodLogIntakePage foodLogIntakePage, BindingResult bindingResult)
  {
    foodLogIntakeValidator.validate(foodLogIntakePage, bindingResult);

    if(bindingResult.hasErrors()){
      return INTAKE_FORM;
    }

    foodLogContract.saveIntake(foodLogIntakePage);
    return "redirect:/food-log/intake";
  }

  @RequestMapping(value = "/measurement-search", method = RequestMethod.GET)
  public @ResponseBody
  ResponseEntity<List<String>> getIngredientSearch(@RequestParam("term") String searchTerm){
    log.debug("SearchTerm=" + searchTerm);
    ResponseEntity<List<String>> result = foodLogContract.getMeasurementUnits(searchTerm);
    log.debug("SearchResponse=" + result);
    return result;
  }
  

}
