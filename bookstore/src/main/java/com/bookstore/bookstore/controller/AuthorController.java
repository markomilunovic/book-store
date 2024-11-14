package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.dto.AuthorDto.AuthorsDto;
import com.bookstore.bookstore.dto.ResponseDto;
import com.bookstore.bookstore.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(
            AuthorService authorService
    ) {
        this.authorService = authorService;
    }

    @Operation(
            summary = "Retrieve authors with optional name filter",
            description = "Retrieves a sorted list of authors, filtering by first name starting letter."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authors fetched successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("all-authors")
    public ResponseEntity<ResponseDto<List<AuthorsDto>>> getAllAuthors(
            @RequestParam("startsWith") String letter
    ) {
        log.info("Received request to fetch authors with filter letter: {}", letter);

        List<AuthorsDto> authors = authorService.getAllAuthors(letter);

        ResponseDto<List<AuthorsDto>> response = new ResponseDto<>(authors, "Authors fetched successfully");
        log.debug("Response content: {}", response);

        return ResponseEntity.ok(response);
    }

}
