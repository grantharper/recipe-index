package org.grantharper.recipe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/health")
public class HealthController
{

  @RequestMapping(value="")
  @ResponseBody
  public ResponseEntity<String> getHealth(){
    return new ResponseEntity<>("system healthy", HttpStatus.OK);
  }
  
}
