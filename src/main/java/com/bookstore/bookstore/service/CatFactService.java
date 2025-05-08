package com.bookstore.bookstore.service;

import com.bookstore.bookstore.dto.CatFactResponseDto;
import com.bookstore.bookstore.exception.CatFactException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatFactService {

    private final RestTemplate restTemplate;

    @Autowired
    public CatFactService(
            RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves a random cat fact from an external API.
     * <p>
     * This method calls the Cat Facts API to fetch a random fact about cats.
     * If the response from the external API is null or does not contain a valid cat fact,
     * a {@link CatFactException} is thrown with the message "No fact available."
     * </p>
     *
     * @return a {@link CatFactResponseDto} containing the retrieved cat fact.
     * @throws CatFactException if the external API responds without providing a valid fact.
     */
    public CatFactResponseDto getCatFact() {
        String url = "https://catfact.ninja/fact";
        CatFactResponseDto response = restTemplate.getForObject(url, CatFactResponseDto.class);

        if (response == null || response.getCatFact() == null) {
            throw new CatFactException("No fact available.");
        }
        return response;
    }

}

