package com.dogsearch.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "category") @Getter @Setter @AllArgsConstructor
public class Category {

    @Id
    @SequenceGenerator(name = "seq_category", sequenceName = "seq_category")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_category")
    private Long id;
    private String name;
}
