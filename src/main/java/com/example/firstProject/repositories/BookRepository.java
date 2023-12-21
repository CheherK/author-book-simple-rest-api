package com.example.firstProject.repositories;

import com.example.firstProject.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, String> {
    BookEntity getBookByIsbn(String isbn);
}
