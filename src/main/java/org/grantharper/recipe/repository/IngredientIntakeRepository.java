package org.grantharper.recipe.repository;

import org.grantharper.recipe.model.IngredientIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientIntakeRepository extends JpaRepository<IngredientIntake, Long>
{
}
