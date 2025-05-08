package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE :letter IS NULL OR LOWER(c.firstName) LIKE LOWER(CONCAT(:letter, '%')) ORDER BY c.firstName, c.lastName")
    List<Customer> findCustomersByFirstNameStartingWith(@Param("letter") String letter);

}
