package com.example.firstProject.services.impl;

import com.example.firstProject.domain.dto.BookDto;
import com.example.firstProject.domain.entities.BookEntity;
import com.example.firstProject.mappers.Mapper;
import com.example.firstProject.repositories.BookRepository;
import com.example.firstProject.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {
    Mapper<BookEntity, BookDto> bookMapper;
    BookRepository bookRepository;
    public BookServiceImpl(Mapper<BookEntity, BookDto> bookMapper, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }
    @Override
    public BookDto createUpdateBook(String isbn ,BookDto bookDto) {
        bookDto.setIsbn(isbn);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        return bookMapper.mapTo(bookRepository.save(bookEntity));
    }
    @Override
    public BookDto partialUpdate(String isbn, BookDto bookDto) {
        bookDto.setIsbn(isbn);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        return bookRepository.findById(isbn).map(existingBook -> {
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
            return bookMapper.mapTo(bookRepository.save(existingBook));
        }).orElseThrow(() -> new RuntimeException("Book does not exist"));
    }
    @Override
    public List<BookDto> getAllBooks() {
        List<BookEntity> bookEntityList = StreamSupport.stream(
                bookRepository.findAll().spliterator(),
                false
        ).collect(Collectors.toList());

        return bookEntityList.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }

    @Override
    public Optional<BookDto> getBookByIsbn(String isbn) {
        return bookRepository.findById(isbn).map(bookMapper::mapTo);
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }
}
