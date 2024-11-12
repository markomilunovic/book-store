package com.bookstore.bookstore.mapper;

import com.bookstore.bookstore.dto.BookDto;
import com.bookstore.bookstore.dto.CreateBookDto;
import com.bookstore.bookstore.entity.Author;
import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    private final AuthorRepository authorRepository;

    @Autowired
    public BookMapper(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Book createBookDtoToBook(CreateBookDto createBookDto) {

        Book book = new Book();
        book.setName(createBookDto.getName());
        book.setOriginalName(createBookDto.getOriginalName());
        book.setTotalPageCount(createBookDto.getPageCount());
        book.setIsbn(createBookDto.getIsbn());
        book.setPublicationDate(createBookDto.getPublicationDate());
        book.setAvailableCopies(createBookDto.getAvailableCopies());
        book.setGenre(createBookDto.getGenre());

        Set<Author> authors = createBookDto.getAuthors().stream()
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

        book.setAuthors(authors);

        return book;
    }

    public BookDto bookToBookDto(Book book) {

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setOriginalName(book.getOriginalName());
        bookDto.setPageCount(book.getTotalPageCount());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPublicationDate(book.getPublicationDate());
        bookDto.setAvailableCopies(book.getAvailableCopies());
        bookDto.setGenre(book.getGenre());

        return bookDto;
    }
}

