package com.bookstore.bookstore.mapper;

import com.bookstore.bookstore.common.util.AuthorUtils;
import com.bookstore.bookstore.common.util.ShelfUtils;
import com.bookstore.bookstore.dto.BookDetailsDto;
import com.bookstore.bookstore.dto.BookDto;
import com.bookstore.bookstore.dto.CreateBookDto;
import com.bookstore.bookstore.dto.UpdateBookDto;
import com.bookstore.bookstore.entity.Author;
import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.entity.Shelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    private final AuthorUtils authorUtils;
    private final ShelfUtils shelfUtils;

    @Autowired
    public BookMapper(
            AuthorUtils authorUtils,
            ShelfUtils shelfUtils
            ) {
        this.authorUtils = authorUtils;
        this.shelfUtils = shelfUtils;
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

        if (createBookDto.getShelfCodes() != null && !createBookDto.getShelfCodes().isEmpty()) {
            Set<Shelf> shelves = shelfUtils.getOrCreateShelves(createBookDto.getShelfCodes());
            book.setShelves(shelves);
        }

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

        if (updateBookDto.getShelfCodes() != null && !updateBookDto.getShelfCodes().isEmpty()) {
            Set<Shelf> shelves = shelfUtils.getOrCreateShelves(updateBookDto.getShelfCodes());
            book.setShelves(shelves);
        }

        return book;
    }

    public BookDetailsDto bookToBookDetailsDto(Book book) {
        BookDetailsDto bookDetailsDto = new BookDetailsDto();
        bookDetailsDto.setId(book.getId());
        bookDetailsDto.setName(book.getName());
        bookDetailsDto.setOriginalName(book.getOriginalName());
        bookDetailsDto.setPageCount(book.getTotalPageCount());
        bookDetailsDto.setIsbn(book.getIsbn());
        bookDetailsDto.setPublicationDate(book.getPublicationDate());
        bookDetailsDto.setAvailableCopies(book.getAvailableCopies());
        bookDetailsDto.setGenre(book.getGenre());

        // Map authors
        bookDetailsDto.setAuthors(
                book.getAuthors().stream()
                        .map(author -> author.getFirstName() + " " + author.getLastName())
                        .collect(Collectors.toSet())
        );

        // Map shelves
        bookDetailsDto.setShelfCodes(
                book.getShelves().stream()
                        .map(Shelf::getCode)
                        .collect(Collectors.toSet())
        );

        return bookDetailsDto;
    }


}

