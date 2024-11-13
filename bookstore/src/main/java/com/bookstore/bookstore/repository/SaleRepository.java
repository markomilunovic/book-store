package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
