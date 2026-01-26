package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.DepartmentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createAndGetDepartment() {
        String url = "http://localhost:" + port + "/api/departments";
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName("IT");
        dto.setDescription("IT Dept");

        ResponseEntity<DepartmentDTO> resp = restTemplate.postForEntity(url, dto, DepartmentDTO.class);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertNotNull(resp.getBody());
        Long id = resp.getBody().getId();
        assertNotNull(id);

        ResponseEntity<DepartmentDTO> getResp = restTemplate.getForEntity(url + "/" + id, DepartmentDTO.class);
        assertEquals(HttpStatus.OK, getResp.getStatusCode());
        assertEquals("IT", getResp.getBody().getName());
    }
}
