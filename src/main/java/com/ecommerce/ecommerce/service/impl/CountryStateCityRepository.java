package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.dto.CityDTO;
import com.ecommerce.ecommerce.dto.CountryDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class CountryStateCityRepository {

    private final WebClient webClient;
    private final String apiKey;

    public CountryStateCityRepository(
            @Value("${csc.api.base-url:https://api.countrystatecity.in/v1}") String baseUrl,
            @Value("${csc.api.key:}") String apiKey
    ) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public List<CityDTO> findCities(String countryIso2, String stateIso2) {
        try {
            CityDTO[] arr = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/countries/{country}/states/{state}/cities").build(countryIso2, stateIso2))
                    .header("X-CSCAPI-KEY", apiKey)
                    .retrieve()
                    .bodyToMono(CityDTO[].class)
                    .block();
            return arr == null ? Collections.emptyList() : Arrays.asList(arr);
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to fetch cities: " + e.getStatusCode() + " " + e.getResponseBodyAsString(), e);
        }
    }
}
