package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.dto.EmployeeDTO;
import com.ecommerce.ecommerce.entity.Department;
import com.ecommerce.ecommerce.entity.Employee;
//import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.repository.DepartmentRepository;
import com.ecommerce.ecommerce.repository.EmployeeRepository;
import com.ecommerce.ecommerce.service.EmployeeService;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<EmployeeDTO> findAll(Long departmentId) {
        List<Employee> employees;
        if (departmentId != null) {
            employees = employeeRepository.findByDepartmentId(departmentId);
        } else {
            employees = employeeRepository.findAll();
        }
        return employees.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO findById(Long id) {
        Employee e = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id=" + id));
        return toDto(e);
    }

    @Override
    public EmployeeDTO create(EmployeeDTO dto) {
        if (dto.getEmail() != null && employeeRepository.existsByEmail(dto.getEmail())) {
            throw new EntityExistsException("Employee with email already exists");
        }
        Employee e = new Employee();
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setEmail(dto.getEmail());
        e.setHireDate(dto.getHireDate());
        if (dto.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(() -> new RuntimeException("Department not found with id=" + dto.getDepartmentId()));
            e.setDepartment(dept);
        }
        Employee saved = employeeRepository.save(e);
        return toDto(saved);
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO dto) {
        Employee e = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id=" + id));
        if (dto.getEmail() != null && !dto.getEmail().equals(e.getEmail()) && employeeRepository.existsByEmail(dto.getEmail())) {
            throw new EntityExistsException("Employee with email already exists");
        }
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setEmail(dto.getEmail());
        e.setHireDate(dto.getHireDate());
        if (dto.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(() -> new RuntimeException("Department not found with id=" + dto.getDepartmentId()));
            e.setDepartment(dept);
        } else {
            e.setDepartment(null);
        }
        Employee saved = employeeRepository.save(e);
        return toDto(saved);
    }

    @Override
    public void delete(Long id) {
        Employee e = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id=" + id));
        employeeRepository.delete(e);
    }

    private EmployeeDTO toDto(Employee e) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(e.getId());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setEmail(e.getEmail());
        dto.setHireDate(e.getHireDate());
        if (e.getDepartment() != null) {
            dto.setDepartmentId(e.getDepartment().getId());
        }
        return dto;
    }
}
