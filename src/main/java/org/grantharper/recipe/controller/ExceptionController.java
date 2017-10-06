package org.grantharper.recipe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class ExceptionController
{

private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);
  
  @ExceptionHandler(value = Exception.class)
  public String errorPage(Model model, Exception exception){
    
    log.error(exception.getMessage(), exception);
    model.addAttribute("error", exception.getMessage());
    return "custom-error";
  }
}
