package org.grantharper.recipe.repository;

import java.util.List;

import org.grantharper.recipe.model.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long>
{
    @Query("select r from Recipe r order by r.pageNumber asc")
    List<Recipe> findAllOrderByPageNumber();

    // Page<Recipe> findAllOrderByPageNumber(Pageable pageable);

    List<Recipe> findByTitleContains(String recipeTitle);

    List<Recipe> findByIngredientsNameContains(String ingredientName);

    List<Recipe> findDistinctRecipeByIngredientsNameContainsOrTitleContainsOrderByPageNumber(String ingredientName,
            String recipeTitle);

}
