package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.authors LEFT JOIN FETCH b.shelves WHERE b.id = :id")
    Optional<Book> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT b FROM Book b WHERE " +
            "(:bookName IS NULL OR LOWER(b.name) LIKE LOWER(CONCAT(:bookName, '%')) OR LOWER(b.originalName) LIKE LOWER(CONCAT(:bookName, '%'))) " +
            "AND (:isbn IS NULL OR b.isbn = :isbn) " +
            "AND (:year IS NULL OR FUNCTION('YEAR', b.publicationDate) = :year)")
    Page<Book> findAllWithFilters(
            @Param("bookName") String bookName,
            @Param("isbn") String isbn,
            @Param("year") Integer year,
            Pageable pageable);


}
