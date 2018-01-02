package org.grantharper.recipe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/react")
public class ReactController
{
    @RequestMapping("")
    public String getReactIndex() {
        return "index-react";
    }
    
}
