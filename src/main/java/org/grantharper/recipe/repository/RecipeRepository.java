package org.grantharper.recipe.repository;

import java.util.List;

import org.grantharper.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>
{
  @Query("select r from Recipe r order by r.pageNumber asc")
  List<Recipe> findAllOrderByPageNumber();

  List<Recipe> findByTitleContains(String recipeTitle);
  
  List<Recipe> findByIngredientsNameContains(String ingredientName);
  
  List<Recipe> findDistinctRecipeByIngredientsNameContainsOrTitleContainsOrderByPageNumber(String ingredientName, String recipeTitle);
  
}
