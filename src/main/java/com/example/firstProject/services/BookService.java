package com.example.firstProject.services;

import com.example.firstProject.domain.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookDto createUpdateBook(String isbn, BookDto bookDto);
    List<BookDto> getAllBooks();

    void deleteBook(String isbn);

    BookDto partialUpdate(String isbn, BookDto bookDto);
    Optional<BookDto> getBookByIsbn(String isbn);

    boolean isExists(String isbn);
}
