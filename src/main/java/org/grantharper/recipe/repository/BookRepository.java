package org.grantharper.recipe.repository;

import org.grantharper.recipe.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>
{

}
