package org.grantharper.recipe.model;

import javax.persistence.*;

@Entity
public class IngredientIntake
{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "intake_id")
  private Long id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "ingredient_id")
  private Ingredient ingredient;

  private Double amountValue;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "unit_id")
  private MeasurementUnit measurementUnit;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Ingredient getIngredient()
  {
    return ingredient;
  }

  public void setIngredient(Ingredient ingredient)
  {
    this.ingredient = ingredient;
  }

  public Double getAmountValue()
  {
    return amountValue;
  }

  public void setAmountValue(Double amountValue)
  {
    this.amountValue = amountValue;
  }

  public MeasurementUnit getMeasurementUnit()
  {
    return measurementUnit;
  }

  public void setMeasurementUnit(MeasurementUnit measurementUnit)
  {
    this.measurementUnit = measurementUnit;
  }
}
