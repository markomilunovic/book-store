package com.bookstore.bookstore.service;

import com.bookstore.bookstore.dto.SaleDto.*;
import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.entity.Customer;
import com.bookstore.bookstore.entity.Sale;
import com.bookstore.bookstore.exception.BookNotFoundException;
import com.bookstore.bookstore.exception.CustomerNotFoundException;
import com.bookstore.bookstore.mapper.SaleMapper;
import com.bookstore.bookstore.repository.BookRepository;
import com.bookstore.bookstore.repository.CustomerRepository;
import com.bookstore.bookstore.repository.SaleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
public class SaleService {

    private final SaleRepository saleRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final SaleMapper saleMapper;

    @Autowired
    public SaleService(
            SaleRepository saleRepository,
            BookRepository bookRepository,
            CustomerRepository customerRepository,
            SaleMapper saleMapper
    ) {
        this.saleRepository = saleRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.saleMapper = saleMapper;
    }

    /**
     * Creates a new sale in the bookstore system.
     * <p>
     * This method takes a {@code CreateSaleDto} object containing essential details for the sale and performs
     * a series of actions to complete the sale process. It verifies that the specified book and customer exist,
     * maps the sale information to a {@code Sale} entity, and persists the sale in the database.
     * Upon successful creation, it returns a {@code SaleDto} representing the sale, with details such as
     * sale ID, purchase date, and other sale attributes.
     * </p>
     * @param createSaleDto The data transfer object containing details required to create the sale,
     *                      including the book ID, customer ID, sale price, and employee code.
     * @return {@link SaleDto} A data transfer object representing the created sale, including the sale ID,
     *         book ID, customer ID, purchase date, sale price, and employee code.
     * @throws BookNotFoundException if the specified book is not found in the system.
     * @throws CustomerNotFoundException if the specified customer is not found in the system.
     */
    public SaleDto createSale(CreateSaleDto createSaleDto) {

        log.info("Creating sale with book ID: {}, customer ID: {}",
                createSaleDto.getBookId(), createSaleDto.getCustomerId());
        log.debug("Received createSaleDto: {}", createSaleDto);

        Book book = bookRepository.findById(createSaleDto.getBookId())
                .orElseThrow(() -> new BookNotFoundException(createSaleDto.getBookId()));

        log.debug("Fetched book details: {}", book);

        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Insufficient copies available for sale.");
        }

        Customer customer = customerRepository.findById(createSaleDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(createSaleDto.getCustomerId()));

        log.debug("Fetched customer details: {}", customer);

        Sale sale = saleMapper.createSaleDtoToSale(createSaleDto, book, customer);
        log.debug("Mapped Sale entity from CreateSaleDto: {}", sale);

        saleRepository.save(sale);
        log.info("Sale saved with ID: {}", sale.getId());

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        SaleDto saleDto = saleMapper.saleToSaleDto(sale);
        log.debug("Mapped SaleDto from Sale entity: {}", saleDto);

        return saleDto;

    }

    /**
     * Retrieves the top employees by book sales within a specified date range.
     *
     * @param dateFrom the starting date and time of the sales range.
     * @param dateTo the ending date and time of the sales range.
     * @return a list of {@link EmployeeSalesDto} containing the employee code and the number
     *         of books sold by each employee within the specified range, limited to the top 10.
     */
    public List<EmployeeSalesDto> getTopEmployeeSales(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return saleRepository.findTopEmployeeSalesWithinDateRange(dateFrom, dateTo, PageRequest.of(0, 10));
    }


    /**
     * Retrieves the top 10 books by total earnings from sales.
     *
     * @return A list of {@link BookSalesEarningsDto} objects, each containing the book name,
     *         total copies sold, and total earnings.
     */
    public List<BookSalesEarningsDto> getTop10BooksByEarnings() {
        return saleRepository.findTop10BooksByEarnings();
    }

    /**
     * Retrieves a list of top sale time slots within the specified date range.
     * Each time slot is represented by a two-hour window and the count of sales within that window.
     *
     * @param dateFrom The start of the date range for querying time slots.
     * @param dateTo The end of the date range for querying time slots.
     * @return A list of TimeSlotDto objects representing each two-hour time slot with its sales count.
     */
    public List<TimeSlotDto> getTopSaleTimeSlots(LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<Object[]> results = saleRepository.findTopSaleTimeSlots(dateFrom, dateTo);

        return results.stream()
                .map(result -> {
                    LocalDateTime start = LocalDateTime.parse(result[0].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00"));
                    return new TimeSlotDto(
                            start,
                            start.plusHours(2),
                            ((Number) result[1]).longValue());
                })
                .collect(Collectors.toList());
    }

}
