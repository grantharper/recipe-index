package org.grantharper.recipe.repository;

import org.grantharper.recipe.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>
{
  Book findByTitle(String title);
}
