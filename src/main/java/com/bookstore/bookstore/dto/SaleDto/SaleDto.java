package com.bookstore.bookstore.dto.SaleDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {

    @Schema(description = "The id of the sale", example = "1")
    private Long id;

    @Schema(description = "The id of the book sold", example = "1")
    private Long bookId;

    @Schema(description = "The id of the customer who bought the book", example = "1")
    private Long customerId;

    @Schema(description = "The date and time of the purchase", example = "2024-11-13T14:30:00")
    private LocalDateTime purchaseDate;

    @Schema(description = "The price at which the book was sold", example = "29.99")
    private Double salePrice;

    @Schema(description = "The code of the employee who processed the sale", example = "AB123C")
    private String employeeCode;

}
