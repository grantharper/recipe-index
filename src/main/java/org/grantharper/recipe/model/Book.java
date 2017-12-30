package org.grantharper.recipe.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Book
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long id;
    
    private String title;
    
    private String author;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Recipe> recipes;
    
}
