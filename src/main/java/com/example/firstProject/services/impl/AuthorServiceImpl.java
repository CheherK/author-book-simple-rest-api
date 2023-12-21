package com.example.firstProject.services.impl;

import com.example.firstProject.domain.dto.AuthorDto;
import com.example.firstProject.domain.entities.AuthorEntity;
import com.example.firstProject.mappers.Mapper;
import com.example.firstProject.repositories.AuthorRepository;
import com.example.firstProject.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;
    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository ,Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity createdAuthor = authorRepository.save(authorEntity);
        return authorMapper.mapTo(createdAuthor);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<AuthorEntity> authorEntityList = StreamSupport.stream(authorRepository
                        .findAll()
                        .spliterator(),
                        false)
                .collect(Collectors.toList());
        return authorEntityList.stream().map(authorMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public AuthorDto updateAuthor(Long id, AuthorDto authorDto) {
        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        return authorMapper.mapTo(authorRepository.save(authorEntity));
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Optional<AuthorDto> getAuthorById(Long id) {
        return authorRepository.findById(id).map(authorMapper::mapTo);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorDto partialUpdate(Long id, AuthorDto authorDto) {
        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        return authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            return authorMapper.mapTo(authorRepository.save(existingAuthor));
        }).orElseThrow(() -> new RuntimeException("Author Does not Exist"));
    }
}
