package org.grantharper.recipe.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Book
{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "book_id")
  private Long id;
  
  private String title;
  
  private String author;
  
  @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
  private Set<Recipe> recipes = new HashSet<>();

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

  public String getAuthor()
  {
    return author;
  }

  public void setAuthor(String author)
  {
    this.author = author;
  }

  public Set<Recipe> getRecipes()
  {
    return recipes;
  }

  public void setRecipes(Set<Recipe> recipes)
  {
    this.recipes = recipes;
  }
  
  
  
}
