package com.bookstore.bookstore.mapper;

import com.bookstore.bookstore.common.util.AuthorUtils;
import com.bookstore.bookstore.dto.BookDto;
import com.bookstore.bookstore.dto.CreateBookDto;
import com.bookstore.bookstore.dto.UpdateBookDto;
import com.bookstore.bookstore.entity.Author;
import com.bookstore.bookstore.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class BookMapper {

    private final AuthorUtils authorUtils;

    @Autowired
    public BookMapper(
            AuthorUtils authorUtils
            ) {
        this.authorUtils = authorUtils;
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

        Set<Author> authors = authorUtils.getOrCreateAuthors(createBookDto.getAuthors());
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

    public Book updateBookFromDto(UpdateBookDto updateBookDto, Book book) {
        if (updateBookDto.getName() != null) {
            book.setName(updateBookDto.getName());
        }
        if (updateBookDto.getOriginalName() != null) {
            book.setOriginalName(updateBookDto.getOriginalName());
        }
        if (updateBookDto.getPageCount() != null) {
            book.setTotalPageCount(updateBookDto.getPageCount());
        }
        if (updateBookDto.getPublicationDate() != null) {
            book.setPublicationDate(updateBookDto.getPublicationDate());
        }
        if (updateBookDto.getAvailableCopies() != null) {
            book.setAvailableCopies(updateBookDto.getAvailableCopies());
        }
        if (updateBookDto.getGenre() != null) {
            book.setGenre(updateBookDto.getGenre());
        }
        if (updateBookDto.getAuthors() != null) {

            Set<Author> authors = authorUtils.getOrCreateAuthors(updateBookDto.getAuthors());
            book.setAuthors(authors);
        }

        return book;
    }

}

