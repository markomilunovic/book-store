package com.bookstore.bookstore.controller;


import com.bookstore.bookstore.dto.BookDto;
import com.bookstore.bookstore.dto.CreateBookDto;
import com.bookstore.bookstore.dto.ResponseDto;
import com.bookstore.bookstore.dto.UpdateBookDto;
import com.bookstore.bookstore.exception.BookAlreadyExistsException;
import com.bookstore.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
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

}
