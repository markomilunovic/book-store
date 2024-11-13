package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.dto.CreateSaleDto;
import com.bookstore.bookstore.dto.ResponseDto;
import com.bookstore.bookstore.dto.SaleDto;
import com.bookstore.bookstore.exception.BookNotFoundException;
import com.bookstore.bookstore.exception.CustomerNotFoundException;
import com.bookstore.bookstore.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/sale")
public class SaleController {

    private final SaleService saleService;

    public SaleController (
            SaleService saleService
    ) {
        this.saleService = saleService;
    }

    @Operation(
            summary = "Create a new sale",
            description = "Creates a new sale and returns the sale details along with a success message."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sale created successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content(schema = @Schema(implementation = BookNotFoundException.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = CustomerNotFoundException.class))),
            @ApiResponse(responseCode = "409", description = "Insufficient copies available for sale",
                    content = @Content(schema = @Schema(implementation = IllegalStateException.class)))
    })
    @PostMapping("create")
    public ResponseEntity<ResponseDto<SaleDto>> createSale(@Valid @RequestBody CreateSaleDto createSaleDto) {

        log.debug("Received request to create sale with data: {}", createSaleDto);
        log.info("Received request to create sale with book ID: {}, customer ID: {}",
                createSaleDto.getBookId(), createSaleDto.getCustomerId());

        SaleDto saleDto = saleService.createSale(createSaleDto);
        ResponseDto<SaleDto> response = new ResponseDto<>(saleDto, "Sale created successfully");

        log.debug("SaleDto created with data: {}", saleDto);
        log.info("Sale created successfully: {}", saleDto.getId());

        return ResponseEntity.ok(response);
    }

}
