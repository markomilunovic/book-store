package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.dto.CustomerDto.CustomerDto;
import com.bookstore.bookstore.dto.ResponseDto;
import com.bookstore.bookstore.service.CustomerService;
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
@RequestMapping("api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(
            CustomerService customerService
    ) {
        this.customerService = customerService;
    }


    @Operation(
            summary = "Retrieve customers with optional name filter",
            description = "Retrieves a sorted list of customers, optionally filtering by first name starting letter."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customers fetched successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/all-customers")
    public ResponseEntity<ResponseDto<List<CustomerDto>>> getAllCustomers(
            @RequestParam(value = "startsWith", required = false) String letter
    ) {
        log.info("Received request to fetch customers with filter letter: {}", letter);

        List<CustomerDto> customers = customerService.getCustomersByFirstNameStartingWith(letter);
        ResponseDto<List<CustomerDto>> response = new ResponseDto<>(customers, "Customers fetched successfully");

        log.debug("Response content: {}", response);

        return ResponseEntity.ok(response);
    }

}
