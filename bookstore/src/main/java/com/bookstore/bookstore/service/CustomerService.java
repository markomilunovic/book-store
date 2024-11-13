package com.bookstore.bookstore.service;

import com.bookstore.bookstore.dto.CustomerDto;
import com.bookstore.bookstore.entity.Customer;
import com.bookstore.bookstore.mapper.CustomerMapper;
import com.bookstore.bookstore.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(
            CustomerRepository customerRepository,
            CustomerMapper customerMapper
    ) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Retrieves a list of customers whose first names start with the specified letter.
     * If no letter is provided, all customers are retrieved.
     *
     * @param letter an optional query parameter specifying the starting letter of the customer's first name
     *               (case-insensitive). If not provided, all customers are returned.
     * @return a ResponseEntity containing a ResponseDto with a list of CustomerDto objects and a success message.
     *         The customers are sorted by first name and last name.
     */
    public List<CustomerDto> getCustomersByFirstNameStartingWith(String letter) {

        log.info("Fetching customers with filter startsWith: {}", letter);

        List<Customer> customers= customerRepository.findCustomersByFirstNameStartingWith(letter);

        log.debug("Customers content: {}", customers);
        log.debug("Fetched {} customer(s) with filter 'startsWith={}'. Customer details: {}", customers.size(), letter, customers);

        return customers.stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();

    }

}
