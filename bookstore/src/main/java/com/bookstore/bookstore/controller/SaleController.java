package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.dto.*;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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


    @Operation(summary = "Get top employees by book sales within date range",
            description = "Returns top 10 employees by book sales within the specified date range.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Top employees fetched successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/top-employees")
    public ResponseEntity<ResponseDto<List<EmployeeSalesDto>>> getTopEmployees(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo) {

        log.info("Received request to fetch employees with params dateFrom: {}, dateTo: {}", dateFrom, dateTo);

        List<EmployeeSalesDto> topEmployees = saleService.getTopEmployeeSales(dateFrom, dateTo);
        ResponseDto<List<EmployeeSalesDto>> response = new ResponseDto<>(topEmployees, "Top employees fetched successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get top 10 books by earnings",
            description = "Returns the top 10 books with the most earnings based on sales.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books fetched successfully")
    })
    @GetMapping("/top-earnings")
    public ResponseEntity<ResponseDto<List<BookSalesEarningsDto>>> getTop10BooksByEarnings() {
        List<BookSalesEarningsDto> topBooks = saleService.getTop10BooksByEarnings();
        ResponseDto<List<BookSalesEarningsDto>> response = new ResponseDto<>(topBooks, "Top 10 books by earnings fetched successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get top book sale time slots", description = "Fetches the top two time slots with the most sales in the specified period.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Time slots fetched successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/top-sale-time-slots")
    public ResponseEntity<ResponseDto<List<TimeSlotDto>>> getTopSaleTimeSlots(
            @RequestParam("dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo) {

        List<TimeSlotDto> topTimeSlots = saleService.getTopSaleTimeSlots(dateFrom, dateTo);
        ResponseDto<List<TimeSlotDto>> response = new ResponseDto<>(topTimeSlots, "Top sale time slots fetched successfully");

        return ResponseEntity.ok(response);
    }


}
