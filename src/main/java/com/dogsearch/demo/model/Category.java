package com.dogsearch.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity @Data
@Getter @Setter
public class Category {

    public static String objectNamePtBr = "Categoria";

    @Id
    @SequenceGenerator(name = "seq_category", sequenceName = "seq_category")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_category")
    private Long id;
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
