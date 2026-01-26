package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.DepartmentDTO;
import com.ecommerce.ecommerce.entity.Department;
import com.ecommerce.ecommerce.repository.DepartmentRepository;
import com.ecommerce.ecommerce.service.impl.DepartmentServiceImpl;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DepartmentServiceTest {

    private DepartmentRepository departmentRepository;
    private DepartmentService departmentService;

    @BeforeEach
    public void setup() {
        departmentRepository = mock(DepartmentRepository.class);
        departmentService = new DepartmentServiceImpl(departmentRepository);
    }

    @Test
    public void create_whenNameExists_thenThrow() {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName("HR");
        when(departmentRepository.existsByName("HR")).thenReturn(true);
        assertThrows(EntityExistsException.class, () -> departmentService.create(dto));
    }

    @Test
    public void findAll_returnsList() {
        when(departmentRepository.findAll()).thenReturn(Collections.singletonList(new Department("HR", "desc")));
        assertFalse(departmentService.findAll().isEmpty());
    }
}
