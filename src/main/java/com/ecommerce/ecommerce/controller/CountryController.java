package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.CountryDTO;
import com.ecommerce.ecommerce.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<CountryDTO>> list() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }
}
