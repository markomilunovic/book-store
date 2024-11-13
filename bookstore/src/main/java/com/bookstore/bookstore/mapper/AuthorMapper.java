package com.bookstore.bookstore.mapper;

import com.bookstore.bookstore.dto.AuthorsDto;
import com.bookstore.bookstore.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorsDto authorToAuthorsDto(Author author) {
        return AuthorsDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

}
