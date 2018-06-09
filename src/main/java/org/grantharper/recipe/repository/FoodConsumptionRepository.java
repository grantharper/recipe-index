package org.grantharper.recipe.repository;

import org.grantharper.recipe.model.Book;
import org.grantharper.recipe.model.FoodConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodConsumptionRepository extends JpaRepository<FoodConsumption, Long>
{

}
