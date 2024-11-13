package com.bookstore.bookstore.service;

import com.bookstore.bookstore.dto.AuthorsDto;
import com.bookstore.bookstore.entity.Author;
import com.bookstore.bookstore.mapper.AuthorMapper;
import com.bookstore.bookstore.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(
            AuthorRepository authorRepository,
            AuthorMapper authorMapper
    ) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<AuthorsDto> getAllAuthors(String letter) {
        log.info("Fetching authors with filter startsWith: {}", letter);

        return authorRepository.findAll().stream()
                .filter(author -> author.getFirstName().toLowerCase().startsWith(letter.toLowerCase()))
                .sorted((a1, a2) -> a1.getFirstName().compareToIgnoreCase(a2.getFirstName()))
                .map(authorMapper::authorToAuthorsDto)
                .collect(Collectors.toList());
    }



}
