package com.bookstore.bookstore.mapper;

import com.bookstore.bookstore.dto.CreateSaleDto;
import com.bookstore.bookstore.dto.SaleDto;
import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.entity.Customer;
import com.bookstore.bookstore.entity.Sale;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SaleMapper {

    public Sale createSaleDtoToSale(CreateSaleDto createSaleDto, Book book, Customer customer) {

        Sale sale = new Sale();
        sale.setSalePrice(createSaleDto.getSalePrice());
        sale.setEmployeeCode(createSaleDto.getEmployeeCode());
        sale.setBook(book);
        sale.setCustomer(customer);
        sale.setPurchaseDate(LocalDateTime.now());

        return sale;
    }

    public SaleDto saleToSaleDto(Sale sale) {
        return SaleDto.builder()
                .id(sale.getId())
                .bookId(sale.getBook().getId())
                .customerId(sale.getCustomer().getId())
                .purchaseDate(sale.getPurchaseDate())
                .salePrice(sale.getSalePrice())
                .employeeCode(sale.getEmployeeCode())
                .build();
    }

}
