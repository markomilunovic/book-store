package com.bookstore.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Represents an author of books in the bookstore.
 * Each author can be associated with multiple books.
 */
@Getter
@Setter
@ToString(exclude = "books")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author")
public class Author {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            name = "first_name",
            nullable = false
    )
    @EqualsAndHashCode.Include
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false
    )
    @EqualsAndHashCode.Include
    private String lastName;

    @ManyToMany(
            mappedBy = "authors"
    )
    private Set<Book> books;
}
