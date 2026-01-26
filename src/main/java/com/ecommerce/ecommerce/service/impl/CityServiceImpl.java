package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.dto.CityDTO;
import com.ecommerce.ecommerce.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CountryStateCityRepository repository;

    public CityServiceImpl(CountryStateCityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CityDTO> getCitiesByState(String countryIso2, String stateIso2) {
        return repository.findCities(countryIso2, stateIso2);
    }
}
