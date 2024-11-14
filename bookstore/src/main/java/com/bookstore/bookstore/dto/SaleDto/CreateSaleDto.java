package com.bookstore.bookstore.dto.SaleDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSaleDto {

    @Schema(description = "The id of the book sold", example = "1")
    @NotNull(message = "Book ID cannot be empty. Please provide a valid book ID.")
    private Long bookId;

    @Schema(description = "The id of the customer who bought the book", example = "1")
    @NotNull(message = "Customer ID cannot be empty. Please provide a valid customer ID.")
    private Long customerId;

    @Schema(description = "The price at which the book was sold", example = "29.99")
    @NotNull(message = "Sale price cannot be empty. Please provide a valid sale price.")
    private Double salePrice;

    @Schema(description = "The code of the employee who processed the sale", example = "AB123C")
    @NotNull(message = "Employee code cannot be empty. Please provide a valid employee ID")
    @Pattern(
            regexp = "^[A-Za-z]{2}\\d{3}[A-Z]$",
            message = "Employee code must be in the format: two letters (case-insensitive), three digits, and an uppercase letter.")
    private String employeeCode;

}
