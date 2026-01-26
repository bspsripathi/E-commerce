package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.CountryDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CountryService {

    private final WebClient webClient;
    private final String apiKey;

    public CountryService(
            @Value("${csc.api.base-url:https://api.countrystatecity.in/v1}") String baseUrl,
            @Value("${csc.api.key:}") String apiKey
    ) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public List<CountryDTO> getAllCountries() {
        try {
            CountryDTO[] arr = webClient.get()
                    .uri("/countries")
                    .header("X-CSCAPI-KEY", apiKey)
                    .retrieve()
                    .bodyToMono(CountryDTO[].class)
                    .block();

            return arr == null ? Collections.emptyList() : Arrays.asList(arr);
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to fetch countries: " + e.getStatusCode() + " " + e.getResponseBodyAsString(), e);
        }
    }
}
