package org.grantharper.recipe.model;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Ingredient implements Comparable<Ingredient>
{


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ingredient_id")
  private Long id;
  
  private String name;
  
  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "ingredients")
  private Set<Recipe> recipes = new TreeSet<>();

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Set<Recipe> getRecipes()
  {
    return recipes;
  }

  public void setRecipes(Set<Recipe> recipes)
  {
    this.recipes = recipes;
  }
  
  

  @Override
  public String toString()
  {
    return "Ingredient [id=" + id + ", name=" + name + "]";
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Ingredient other = (Ingredient) obj;
    if(other.getName().equalsIgnoreCase(this.getName())){
      return true;
    }
    
    return false;
  }

  @Override
  public int compareTo(Ingredient other)
  {
    return other.getName().compareTo(this.getName());
  }

  
  
  
  
}
