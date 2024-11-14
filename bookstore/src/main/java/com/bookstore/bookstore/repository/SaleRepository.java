package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.dto.BookSalesEarningsDto;
import com.bookstore.bookstore.dto.EmployeeSalesDto;
import com.bookstore.bookstore.entity.Sale;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.bookstore.bookstore.dto.EmployeeSalesDto(s.employeeCode, COUNT(s.id)) " +
            "FROM Sale s " +
            "WHERE s.purchaseDate BETWEEN :dateFrom AND :dateTo " +
            "GROUP BY s.employeeCode " +
            "ORDER BY COUNT(s.id) DESC")
    List<EmployeeSalesDto> findTopEmployeeSalesWithinDateRange(
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            org.springframework.data.domain.Pageable pageable
    );


    @Query("SELECT new com.bookstore.bookstore.dto.BookSalesEarningsDto(" +
            "b.name, COUNT(s.id), SUM(s.salePrice)) " +
            "FROM Sale s JOIN s.book b " +
            "GROUP BY b.id, b.name " +
            "ORDER BY SUM(s.salePrice) DESC")
    List<BookSalesEarningsDto> findTop10BooksByEarnings();


    @Query(value = """
    SELECT
        DATE_FORMAT(s.purchase_date, '%Y-%m-%d %H:00:00') + INTERVAL (HOUR(s.purchase_date) DIV 2) * 2 HOUR AS timeSlotStart,
        COUNT(s.id) AS saleCount
    FROM
        sale s
    WHERE
        s.purchase_date BETWEEN :dateFrom AND :dateTo
    GROUP BY
        timeSlotStart
    ORDER BY
        saleCount DESC
    LIMIT 2
    """, nativeQuery = true)
    List<Object[]> findTopSaleTimeSlots(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

}
