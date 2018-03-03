package org.grantharper.recipe.validator

import spock.lang.*

class RecipeValidatorSpec extends Specification {
    
    RecipeValidator rv = new RecipeValidator();
    
    @Unroll("Recipe title #input should be valid: #expected")
    def "validate title of recipe" () {
        
        when: "user input is checked for valid characters"
        def result = rv.isValidTitle(input)
        
        then: "appropriate determination is made for validity"
        result == expected
        
        where: "following are potential input and expected results"
        
        input                                       || expected
        "recipe"                                    || true
        "beans, bread, milk"                        || true
        "<script>"                                  || false
        "hazelnut salad (with lots of dressing)"    || true
        "<>[]{}"                                    || false
        
    }
    
    @Unroll("Page number #input is valid number: #expected")
    def "validate page numbers" () {
        
        when: "page number is provided by the user"
        def result = rv.isValidPageNumber(input)
        
        then: "appropriate determination is made for validity"
        result == expected
        
        where: "following are potential input and expected results"
        
        input       || expected
        "96"        || true
        "1a"        || false
        "<"         || false
        
    }
    
    @Unroll("Ingredient #input is valid: #expected")
    def "validate ingredient input" () {
        
        when: "ingredient is provided by the user"
        def result = rv.isValidIngredients(input)
        
        then: "appropriate determination is made for validity"
        result == expected
        
        where: "following are potential input and expected results"
        
        input                           || expected
        "shallots; onions; apples"      || true
        "pork, shoulder of; onions"     || true
        "pork (boneless)"               || true
        "pork < chicken"                || false
        
    }
    
}