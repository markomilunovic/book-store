package com.bookstore.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a book entity in the bookstore.
 * Each book has an associated genre, publication details, and can be stored on multiple shelves.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            nullable = false
    )
    private String name;

    @Column(
            name = "original_name",
            nullable = false
    )
    private String originalName;

    @Column(
            name = "total_page_count",
            nullable = false
    )
    private int totalPageCount;

    @Column(
            nullable = false,
            unique = true
    )
    private String isbn;

    @Column(
            name = "publication_date",
            nullable = false
    )
    private LocalDate publicationDate;

    @Column(
            name = "available_copies",
            nullable = false
    )
    private int availableCopies;

    @Column(
            name = "genre"
    )
    private String genre;

    @ManyToMany
    @JoinTable(
            name = "book_shelves",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "shelf_id")
    )
    private Set<Shelf> shelves;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

}
