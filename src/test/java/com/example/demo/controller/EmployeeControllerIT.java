package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createAndGetEmployee() {
        String deptUrl = "http://localhost:" + port + "/api/departments";
        // create department
        com.ecommerce.ecommerce.dto.DepartmentDTO dept = new com.ecommerce.ecommerce.dto.DepartmentDTO();
        dept.setName("Finance");
        dept.setDescription("Finance Dept");
        ResponseEntity<com.ecommerce.ecommerce.dto.DepartmentDTO> deptResp = restTemplate.postForEntity(deptUrl, dept, com.ecommerce.ecommerce.dto.DepartmentDTO.class);
        assertEquals(HttpStatus.CREATED, deptResp.getStatusCode());
        Long deptId = deptResp.getBody().getId();

        String empUrl = "http://localhost:" + port + "/api/employees";
        EmployeeDTO emp = new EmployeeDTO();
        emp.setFirstName("John");
        emp.setLastName("Doe");
        emp.setEmail("john.doe@example.com");
        emp.setHireDate(LocalDate.now());
        emp.setDepartmentId(deptId);

        ResponseEntity<EmployeeDTO> empResp = restTemplate.postForEntity(empUrl, emp, EmployeeDTO.class);
        assertEquals(HttpStatus.CREATED, empResp.getStatusCode());
        Long empId = empResp.getBody().getId();
        assertNotNull(empId);

        ResponseEntity<EmployeeDTO> getResp = restTemplate.getForEntity(empUrl + "/" + empId, EmployeeDTO.class);
        assertEquals(HttpStatus.OK, getResp.getStatusCode());
        assertEquals("john.doe@example.com", getResp.getBody().getEmail());
    }
}
