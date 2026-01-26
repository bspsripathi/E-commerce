package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.CityDTO;

import java.util.List;

public interface CityService {
    List<CityDTO> getCitiesByState(String countryIso2, String stateIso2);
}
