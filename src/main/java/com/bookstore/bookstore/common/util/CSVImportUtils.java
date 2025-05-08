package com.bookstore.bookstore.common.util;

import com.bookstore.bookstore.dto.AuthorDto.AuthorDto;
import com.bookstore.bookstore.dto.BookDto.CreateBookDto;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CSVImportUtils {

    public static Set<CreateBookDto> parseCSV(InputStreamReader reader) throws IOException, CsvValidationException {
        Set<CreateBookDto> books = new HashSet<>();
        try (CSVReader csvReader = new CSVReader(reader)) {
            String[] header = csvReader.readNext();
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                books.add(mapCsvRecordToCreateBookDto(line));
            }
        }
        return books;
    }

    public static CreateBookDto mapCsvRecordToCreateBookDto(String[] record) {
        return CreateBookDto.builder()
                .name(record[0])
                .originalName(record[1])
                .pageCount(Integer.parseInt(record[2]))
                .isbn(record[3])
                .publicationDate(LocalDate.parse(record[4]))
                .availableCopies(Integer.parseInt(record[5]))
                .genre(record[6])
                .authors(parseAuthors(record[7]))
                .shelfCodes(parseShelfCodes(record[8]))
                .build();
    }

    public static Set<AuthorDto> parseAuthors(String authors) {
        return Arrays.stream(authors.split(";"))
                .map(name -> new AuthorDto(name.split(" ")[0], name.split(" ")[1]))
                .collect(Collectors.toSet());
    }

    public static Set<String> parseShelfCodes(String shelfCodes) {
        return Arrays.stream(shelfCodes.split(","))
                .collect(Collectors.toSet());
    }
}
