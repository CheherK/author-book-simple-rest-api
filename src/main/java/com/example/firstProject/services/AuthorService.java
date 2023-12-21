package com.example.firstProject.services;

import com.example.firstProject.domain.dto.AuthorDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorDto createAuthor(AuthorDto authorDto);

    List<AuthorDto> getAllAuthors();

    AuthorDto updateAuthor(Long id, AuthorDto authorDto);

    void deleteAuthor(Long id);

    Optional<AuthorDto> getAuthorById(Long id);

    boolean isExists(Long id);

    AuthorDto partialUpdate(Long id, AuthorDto authorDto);
}
