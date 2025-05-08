package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.dto.CatFactResponseDto;
import com.bookstore.bookstore.dto.ResponseDto;
import com.bookstore.bookstore.exception.CatFactException;
import com.bookstore.bookstore.service.CatFactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

@RestController
@RequestMapping("/api/cat")
public class CatFactController {

    private final CatFactService catFactService;

    @Autowired
    public CatFactController(CatFactService catFactService) {
        this.catFactService = catFactService;
    }

    @Operation(
            summary = "Retrieve a random cat fact",
            description = "Fetches a random fact about cats from an external API. " +
                    "If the external service is unavailable or times out, a fallback message will be returned."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cat fact retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No facts available",
                    content = @Content(schema = @Schema(implementation = CatFactException.class))),
            @ApiResponse(responseCode = "503", description = "Cat fact service unavailable",
                    content = @Content(schema = @Schema(implementation = ResourceAccessException.class)))
    })
    @GetMapping("/get-cat-fact")
    public ResponseEntity<ResponseDto<CatFactResponseDto>> getCatFact() {
        CatFactResponseDto catFact = catFactService.getCatFact();
        ResponseDto<CatFactResponseDto> response = new ResponseDto<>(catFact, "Cat fact retrieved successfully");
        return ResponseEntity.ok(response);
    }
}
