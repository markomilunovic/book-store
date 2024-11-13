package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.dto.EmployeeSalesDto;
import com.bookstore.bookstore.entity.Sale;
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

}
