package com.bookstore.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a sale in the bookstore.
 * Each sale records the details of a book sold, customer, and the employee involved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "book_id",
            nullable = false
    )
    private Book book;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            nullable = false
    )
    private Customer customer;

    @Column(
            name = "purchase_date",
            nullable = false
    )
    private LocalDateTime purchaseDate;

    @Column(
            name = "sale_price",
            nullable = false
    )
    private Double salePrice;

    @Column(
            name = "employee_code",
            nullable = false
    )
    private String employeeCode;

}
