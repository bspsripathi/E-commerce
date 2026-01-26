package com.ecommerce.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DepartmentDTO {
    private Long id;

    @NotBlank
    @Size(max = 100 , message = "Name must be at most 100 characters")
    private String name;

    @Size(max = 500)
    private String description;

    private List<EmployeeDTO> employees = new ArrayList<>();

}
