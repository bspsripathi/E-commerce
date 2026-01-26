package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.CityDTO;
import com.ecommerce.ecommerce.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/countries/{countryIso2}/states/{stateIso2}/cities")
    public ResponseEntity<List<CityDTO>> getCities(@PathVariable String countryIso2, @PathVariable String stateIso2) {
        return ResponseEntity.ok(cityService.getCitiesByState(countryIso2, stateIso2));
    }
}
