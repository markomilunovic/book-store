package com.bookstore.bookstore.mapper;

import com.bookstore.bookstore.dto.CustomerDto;
import com.bookstore.bookstore.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDto customerToCustomerDto(Customer customer) {

        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .address(customer.getAddress())
                .email(customer.getEmail())
                .build();

    }


}
