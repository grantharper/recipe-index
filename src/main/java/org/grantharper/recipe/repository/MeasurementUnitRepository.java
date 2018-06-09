package org.grantharper.recipe.repository;

import org.grantharper.recipe.model.MeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementUnitRepository extends JpaRepository<MeasurementUnit, Long>
{
  MeasurementUnit findByUnitText(String unitText);

  List<MeasurementUnit> findTop5ByUnitTextContainsOrderByUnitTextAsc(String unitText);
}
