package org.grantharper.recipe.repository;

import java.util.List;

import org.grantharper.recipe.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>
{
    public Ingredient findByName(String name);

    @Query("select i from Ingredient i order by i.name asc")
    List<Ingredient> findAllOrderByName();

    List<Ingredient> findTop5ByNameContainsOrderByNameAsc(String ingredientName);
}
