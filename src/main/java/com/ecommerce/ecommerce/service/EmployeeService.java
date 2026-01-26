package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> findAll(Long departmentId);
    EmployeeDTO findById(Long id);
    EmployeeDTO create(EmployeeDTO dto);
    EmployeeDTO update(Long id, EmployeeDTO dto);
    void delete(Long id);
}
