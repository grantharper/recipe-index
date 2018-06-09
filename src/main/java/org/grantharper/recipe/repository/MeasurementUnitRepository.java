package org.grantharper.recipe.repository;

import org.grantharper.recipe.model.MeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementUnitRepository extends JpaRepository<MeasurementUnit, Long>
{
  MeasurementUnit findByUnitText(String unitText);
}
