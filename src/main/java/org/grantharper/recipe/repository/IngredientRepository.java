package org.grantharper.recipe.repository;

import java.util.List;

import org.grantharper.recipe.model.Ingredient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long>
{
    public Ingredient findByName(@Param("name") String name);

    @Query("select i from Ingredient i order by i.name asc")
    List<Ingredient> findAllOrderByName();

    List<Ingredient> findTop5ByNameContainsOrderByNameAsc(String ingredientName);
}
