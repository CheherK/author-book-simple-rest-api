package com.example.firstProject.controllers;

import com.example.firstProject.domain.dto.AuthorDto;
import com.example.firstProject.domain.entities.AuthorEntity;
import com.example.firstProject.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id).map(foundAuthor -> new ResponseEntity<>(foundAuthor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto newAuthor) {
        return new ResponseEntity<>(authorService.createAuthor(newAuthor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(
            @PathVariable Long id,
            @RequestBody AuthorDto authorDto
            ) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(authorService.updateAuthor(id, authorDto), HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(authorService.partialUpdate(id, authorDto), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAuthorById(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
