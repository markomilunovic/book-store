package com.bookstore.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Represents a shelf in the bookstore where books are stored.
 * Each shelf has a unique code and a section designation, and can hold multiple books.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shelf")
public class Shelf {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private String code;

    @Column(
            nullable = false
    )
    private String section;

    @ManyToMany(
            mappedBy = "shelves"
    )
    private Set<Book> books;

}
