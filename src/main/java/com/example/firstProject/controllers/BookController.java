package com.example.firstProject.controllers;

import com.example.firstProject.domain.dto.BookDto;
import com.example.firstProject.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable String isbn) {
        Optional<BookDto> foundBook = bookService.getBookByIsbn(isbn);
        return foundBook.map(bookDto -> new ResponseEntity<>(bookDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(
            @PathVariable String isbn,
            @RequestBody BookDto bookDto) {
        if (bookService.isExists(isbn)) {
            return new ResponseEntity<>(bookService.createUpdateBook(isbn, bookDto), HttpStatus.OK);
        }
        return new ResponseEntity<>(bookService.createUpdateBook(isbn, bookDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable String isbn,
            @RequestBody BookDto bookDto
    ) {
        if (!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookService.partialUpdate(isbn, bookDto), HttpStatus.OK);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
