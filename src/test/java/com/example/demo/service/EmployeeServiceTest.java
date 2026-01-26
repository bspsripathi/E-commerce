package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.EmployeeDTO;
import com.ecommerce.ecommerce.entity.Employee;
import com.ecommerce.ecommerce.repository.DepartmentRepository;
import com.ecommerce.ecommerce.repository.EmployeeRepository;
import com.ecommerce.ecommerce.service.impl.EmployeeServiceImpl;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        employeeRepository = mock(EmployeeRepository.class);
        departmentRepository = mock(DepartmentRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository, departmentRepository);
    }

    @Test
    public void create_whenEmailExists_thenThrow() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmail("a@b.com");
        when(employeeRepository.existsByEmail("a@b.com")).thenReturn(true);
        assertThrows(EntityExistsException.class, () -> employeeService.create(dto));
    }

    @Test
    public void findAll_returnsList() {
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(new Employee("F", "L", "a@b.com", LocalDate.now())));
        assertFalse(employeeService.findAll(null).isEmpty());
    }
}
