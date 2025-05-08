package com.bookstore.bookstore.dto.SaleDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing the sales performance of an employee.
 * <p>
 * This DTO provides information about the total books sold by an employee,
 * identified by their unique code.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSalesDto {

    @Schema(description = "The code of the employee", example = "AB123C")
    private String employeeCode;

    @Schema(description = "The number of books sold", example = "5")
    private Long booksSold;

}
