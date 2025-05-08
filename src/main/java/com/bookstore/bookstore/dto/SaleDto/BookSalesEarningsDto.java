package com.bookstore.bookstore.dto.SaleDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing the sales and earnings details of a book.
 * <p>
 * This DTO provides summarized information about a book's sales, including the book's
 * name, the total number of copies sold, and the total earnings generated from its sales.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSalesEarningsDto {

    @Schema(description = "The name of the book", example = "The Divine Comedy")
    private String bookName;

    @Schema(description = "Total number of copies sold for this book", example = "5")
    private Long copiesSold;

    @Schema(description = "Total earnings generated from the sales of this book", example = "45.72")
    private Double totalEarnings;
}
