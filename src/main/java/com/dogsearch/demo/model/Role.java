package com.dogsearch.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity @Data
@Getter
@Setter
@AllArgsConstructor
public class Role {

    @Id
    @SequenceGenerator(name = "seq_role", sequenceName = "seq_role")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_role")
    private Long id;
    private String role;
}
