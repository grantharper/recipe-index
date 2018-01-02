package org.grantharper.recipe.repository;

import org.grantharper.recipe.model.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long>
{

}
