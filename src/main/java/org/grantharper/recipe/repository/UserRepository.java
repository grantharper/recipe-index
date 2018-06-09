package org.grantharper.recipe.repository;

import org.grantharper.recipe.model.RecipeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<RecipeUser, Long>
{
  RecipeUser findByUsername(String username);
}
