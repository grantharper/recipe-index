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

}
