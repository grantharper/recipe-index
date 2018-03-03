package org.grantharper.recipe.validator

import spock.lang.*

class RecipeValidatorSpec extends Specification {
    
    @Unroll("Recipe title #input should be valid: #expected")
    def "validate title of recipe" () {
        
        given: "Recipe Validator"
        RecipeValidator rv = new RecipeValidator();
        
        when: "user input is checked for valid characters"
        def result = rv.isValidTitle(input)
        
        then: "appropriate determination is made for validity"
        result == expected
        
        where: "following are potential input and expected result combinations"
        
        input       || expected
        "recipe"    || true
        "cool, yeah"|| true
        "<script>"  || false
        
        
        
    }
    
    
}