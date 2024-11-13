package com.bookstore.bookstore.controller;


import com.bookstore.bookstore.dto.*;
import com.bookstore.bookstore.exception.BookAlreadyExistsException;
import com.bookstore.bookstore.exception.BookNotFoundException;
import com.bookstore.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(
            BookService bookService
    ) {
        this.bookService = bookService;
    }

    @Operation(
            summary = "Create a new book",
            description = "Creates a new book and returns the book details along with a success message."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book created successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Book already exists",
                    content = @Content(schema = @Schema(implementation = BookAlreadyExistsException.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto<BookDto>> createBook(@Valid @RequestBody CreateBookDto createBookDto) {

        log.debug("Received request to create book with data: {}", createBookDto);
        log.info("Received request to create book with ISBN: {}", createBookDto.getIsbn());



        BookDto bookDto = bookService.createBook(createBookDto);

        log.debug("BookDto created with data: {}", bookDto);
        log.info("Book created successfully: {}", bookDto.getId());

        ResponseDto<BookDto> response = new ResponseDto<>(bookDto, "Book created successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update an existing book",
            description = "Updates the details of an existing book by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book updated successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Book already exists",
                    content = @Content(schema = @Schema(implementation = BookAlreadyExistsException.class))),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content(schema = @Schema(implementation = BookNotFoundException.class)))
    })
    @PutMapping("update/{id}")
    public ResponseEntity<ResponseDto<BookDto>> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookDto updateBookDto) {

        log.info("Received request to update book with ID: {}", id);
        log.debug("Received request to update book with data: {}", updateBookDto);

        BookDto updatedBook = bookService.updateBook(id, updateBookDto);

        log.debug("BookDto updated with data: {}", updateBookDto);
        log.info("Book updated successfully: {}", updatedBook.getId());

        ResponseDto<BookDto> response = new ResponseDto<>(updatedBook, "Book updated successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete a book by ID",
            description = "Deletes a book record by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content(schema = @Schema(implementation = BookNotFoundException.class)))
    })
    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteBookById(@PathVariable Long id) {
        log.info("Request received to delete book with ID: {}", id);
        bookService.deleteBookById(id);
        ResponseDto<Void> response = new ResponseDto<>(null, "Book deleted successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get book details by ID",
            description = "Fetches the complete details of a book by its ID, including authors, genre, and shelves."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book details fetched successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content(schema = @Schema(implementation = BookNotFoundException.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<BookDetailsDto>> getBookById(@PathVariable Long id) {

        log.info("Request received to fetch book details with ID: {}", id);

        BookDetailsDto bookDetailsDto = bookService.getBookById(id);
        ResponseDto<BookDetailsDto> response = new ResponseDto<>(bookDetailsDto, "Book details fetched successfully");

        return ResponseEntity.ok(response);

    }

    @Operation(
            summary = "Increase the number of copies of a book",
            description = "Increases the available copies of a book by a specified count."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Number of copies increased successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content(schema = @Schema(implementation = BookNotFoundException.class)))
    })
    @PatchMapping("/{id}/increase-copies")
    public ResponseEntity<ResponseDto<Void>> increaseBookCopies(@PathVariable Long id, @RequestParam int count) {

        log.info("Request received to increase copies for book ID: {} by {}", id, count);
        bookService.increaseBookCopies(id, count);
        ResponseDto<Void> response = new ResponseDto<>(null, "Number of copies increased successfully");

        return ResponseEntity.ok(response);

    }

    @Operation(
            summary = "Get a paginated list of books with filters",
            description = "Fetches a list of books based on optional filters such as book name, ISBN, and publication year, with pagination support."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books fetched successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/list")
    public ResponseEntity<ResponseDto<Page<BookDto>>> getBooks(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer year,
            Pageable pageable) {

        log.info("Received request to fetch books with filters - Name: {}, ISBN: {}, Year: {}, Page: {}",
                bookName, isbn, year, pageable);

        Page<BookDto> books = bookService.getBooks(bookName, isbn, year, pageable);
        ResponseDto<Page<BookDto>> response = new ResponseDto<>(books, "Books fetched successfully");

        log.debug("Response content: {}", response);

        return ResponseEntity.ok(response);
    }

}
