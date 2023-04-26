package com.dogsearch.demo.dto.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class PersonRoleDTO {
    private Long id;
    private String name;
    private String role;
}
