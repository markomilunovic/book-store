package com.bookstore.bookstore.dto.SaleDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing a two-hour time slot for sales activity.
 * This DTO includes the start and end times of the time slot, as well as the
 * total count of sales that occurred within this period.
 */
@Data
@AllArgsConstructor
public class TimeSlotDto {

    @Schema(
            description = "Start time of the two-hour time slot.",
            example = "2024-11-14T10:00:00"
    )
    private LocalDateTime start;

    @Schema(
            description = "End time of the two-hour time slot, exactly two hours after the start.",
            example = "2024-11-14T12:00:00"
    )
    private LocalDateTime end;

    @Schema(
            description = "Number of sales recorded within this two-hour time slot.",
            example = "15"
    )
    private Long saleCount;
}

