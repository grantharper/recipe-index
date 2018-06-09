package org.grantharper.recipe.model;


import javax.persistence.*;

@Entity
public class MeasurementUnit
{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "unit_id")
  private Long id;

  private String unitText;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getUnitText()
  {
    return unitText;
  }

  public void setUnitText(String unitText)
  {
    this.unitText = unitText;
  }
}
