package com.bookstore.bookstore.service;

import com.bookstore.bookstore.dto.BookDto;
import com.bookstore.bookstore.dto.CreateBookDto;
import com.bookstore.bookstore.dto.UpdateBookDto;
import com.bookstore.bookstore.exception.BookAlreadyExistsException;
import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.exception.BookNotFoundException;
import com.bookstore.bookstore.mapper.BookMapper;
import com.bookstore.bookstore.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(
            BookRepository bookRepository,
            BookMapper bookMapper
    ) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }


    /**
     * Creates a new book record in the system.
     * <p>
     * This method accepts a {@link CreateBookDto} object containing details of the book to be created,
     * such as name, ISBN, and author information. It performs a check to ensure that a book with the
     * specified ISBN does not already exist. If a duplicate ISBN is found, a {@link BookAlreadyExistsException}
     * is thrown. If the ISBN is unique, the method maps the DTO to a {@link Book} entity, saves it to the
     * repository, and then maps the saved entity back to a {@link BookDto} for the response.
     * </p>
     *
     * @param createBookDto the data transfer object containing the details of the book to create
     * @return a {@link BookDto} representing the created book with its generated ID and other details
     * @throws BookAlreadyExistsException if a book with the specified ISBN already exists in the database
     */
    public BookDto createBook(CreateBookDto createBookDto) {

        log.info("Creating book with ISBN: {}", createBookDto.getIsbn());
        log.debug("Received CreateBookDto: {}", createBookDto);

        if (bookRepository.existsByIsbn(createBookDto.getIsbn())) {
            throw new BookAlreadyExistsException(createBookDto.getIsbn());
        }

        Book book = bookMapper.createBookDtoToBook(createBookDto);
        log.debug("Mapped Book entity from CreateBookDto: {}", book);

        book = bookRepository.save(book);
        log.info("Book saved with ID: {}", book.getId());

        BookDto bookDto = bookMapper.bookToBookDto(book);
        log.debug("Mapped BookDto from Book entity: {}", bookDto);

        return bookDto;
    }

    /**
     * Updates an existing book record in the system.
     * <p>
     * This method retrieves a {@link Book} entity by its ID and updates its fields based on the
     * provided {@link UpdateBookDto}. If the book with the specified ID does not exist, a
     * {@link BookNotFoundException} is thrown. If the book with the same ISBN already exists,
     * a {@link BookAlreadyExistsException} is thrown .The updated {@link Book} entity is then saved to
     * the repository and mapped to a {@link BookDto} to be returned as the response.
     * </p>
     *
     * @param bookId the ID of the book to be updated
     * @param updateBookDto the data transfer object containing updated details for the book
     * @return a {@link BookDto} representing the updated book with the modified fields
     * @throws BookNotFoundException if no book with the specified ID is found in the repository
     * @throws BookAlreadyExistsException if a book with the specified ISBN already exists in the database
     */
    public BookDto updateBook(Long bookId, UpdateBookDto updateBookDto) {

        log.info("Updating book with ISBN: {}", updateBookDto.getIsbn());
        log.debug("Received UpdateBookDto: {}", updateBookDto);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if (bookRepository.existsByIsbn(updateBookDto.getIsbn())) {
            throw new BookAlreadyExistsException(updateBookDto.getIsbn());
        }

        book = bookMapper.updateBookFromDto(updateBookDto, book);
        log.debug("Mapped Book entity from UpdateBookDto: {}", book);

        book = bookRepository.save(book);
        log.info("Updated Book saved with ID: {}", book.getId());

        BookDto updatedBookDto = bookMapper.bookToBookDto(book);
        log.debug("Mapped updated BookDto from Book entity: {}", updatedBookDto);

        return updatedBookDto;
    }

}
