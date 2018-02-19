package org.grantharper.recipe.model;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Recipe implements Comparable<Recipe>
{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "recipe_id")
  private Long id;

  private String title;

  private Integer pageNumber;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "recipe_ingredient", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
  private Set<Ingredient> ingredients = new TreeSet<>();
  
  @ManyToOne
  private Book book;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public Integer getPageNumber()
  {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber)
  {
    this.pageNumber = pageNumber;
  }

  public Set<Ingredient> getIngredients()
  {
    return ingredients;
  }

  public void setIngredients(Set<Ingredient> ingredients)
  {
    this.ingredients = ingredients;
  }

  public Book getBook()
  {
    return book;
  }

  public void setBook(Book book)
  {
    this.book = book;
  }

  @Override
  public String toString()
  {
    return "Recipe [id=" + id + ", title=" + title + ", pageNumber=" + pageNumber + "]";
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    
    result = prime * result + ((pageNumber == null) ? 0 : pageNumber.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
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
    Recipe other = (Recipe) obj;
    if (id == null)
    {
      if (other.id != null)
        return false;
    } 
    else if (id.equals(other.id))
    {
      return true;
    }
   
    return false;

  }

  @Override
  public int compareTo(Recipe other)
  {
    return this.getTitle().compareTo(other.getTitle());
  }

}
