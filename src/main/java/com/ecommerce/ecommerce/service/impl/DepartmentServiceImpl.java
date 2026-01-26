package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.dto.DepartmentDTO;
import com.ecommerce.ecommerce.dto.EmployeeDTO;
import com.ecommerce.ecommerce.entity.Department;
import com.ecommerce.ecommerce.entity.Employee;
//import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.repository.DepartmentRepository;
import com.ecommerce.ecommerce.service.DepartmentService;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDTO> findAll() {
        return departmentRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO findById(Long id) {
        Department dept = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found with id=" + id));
        return toDto(dept);
    }

    @Override
    public DepartmentDTO create(DepartmentDTO dto) {
        if (dto.getName() != null && departmentRepository.existsByName(dto.getName())) {
            throw new EntityExistsException("Department with name already exists");
        }
        Department dept = new Department();
        dept.setName(dto.getName());
        dept.setDescription(dto.getDescription());
        Department saved = departmentRepository.save(dept);
        return toDto(saved);
    }

    @Override
    public DepartmentDTO update(Long id, DepartmentDTO dto) {
        Department dept = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found with id=" + id));
        if (dto.getName() != null && !dto.getName().equals(dept.getName()) && departmentRepository.existsByName(dto.getName())) {
            throw new EntityExistsException("Department with name already exists");
        }
        dept.setName(dto.getName());
        dept.setDescription(dto.getDescription());
        Department saved = departmentRepository.save(dept);
        return toDto(saved);
    }

    @Override
    public void delete(Long id) {
        Department dept = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found with id=" + id));
        departmentRepository.delete(dept);
    }

    private DepartmentDTO toDto(Department dept) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(dept.getId());
        dto.setName(dept.getName());
        dto.setDescription(dept.getDescription());
        // map employees shallowly
        dto.setEmployees(dept.getEmployees().stream().map(this::employeeToDto).collect(Collectors.toList()));
        return dto;
    }

    private EmployeeDTO employeeToDto(Employee e) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(e.getId());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setEmail(e.getEmail());
        dto.setHireDate(e.getHireDate());
        dto.setDepartmentId(e.getDepartment() != null ? e.getDepartment().getId() : null);
        return dto;
    }
}
