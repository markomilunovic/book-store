package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {

    Optional<Shelf> findByCode(String code);

}
