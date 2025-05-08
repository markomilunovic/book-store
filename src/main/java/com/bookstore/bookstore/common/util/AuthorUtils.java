package com.bookstore.bookstore.common.util;

import com.bookstore.bookstore.dto.AuthorDto.AuthorDto;
import com.bookstore.bookstore.entity.Author;
import com.bookstore.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorUtils {

    public final AuthorRepository authorRepository;

    @Autowired
    public AuthorUtils(
            AuthorRepository authorRepository
    ) {
        this.authorRepository = authorRepository;
    }

    /**
     * Retrieves or creates authors based on a set of {@link AuthorDto} objects.
     * <p>
     * For each {@link AuthorDto} in the provided set, this method checks the repository to see if an
     * author with the specified first and last name already exists. If an author is found, it is
     * returned; otherwise, a new author is created, saved to the repository, and returned. The method
     * ensures that only unique authors (by first and last name) are included in the resulting set.
     * </p>
     *
     * @param authorDtos a set of {@link AuthorDto} objects representing authors to be retrieved or created
     * @return a set of {@link Author} entities that are either retrieved from the repository or newly created
     */
    public Set<Author> getOrCreateAuthors(Set<AuthorDto> authorDtos) {
        return authorDtos.stream()
                .map(authorDto -> authorRepository.findByFirstNameAndLastName(
                        authorDto.getFirstName(),
                        authorDto.getLastName()
                ).orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setFirstName(authorDto.getFirstName());
                    newAuthor.setLastName(authorDto.getLastName());
                    return authorRepository.save(newAuthor);
                }))
                .collect(Collectors.toSet());
    }
}
