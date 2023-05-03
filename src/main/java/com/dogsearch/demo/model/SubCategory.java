package com.dogsearch.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Data
@Getter @Setter @RequiredArgsConstructor
public class SubCategory {

    public static String objectNamePtBr = "Sub-Categoria";

    @Id
    @SequenceGenerator(name = "seq_sub-category", sequenceName = "seq_sub-category")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_sub-category")
    private Long id;
    @NonNull
    private String name;
    @NonNull @ManyToOne @JoinColumn(name = "category_id")
    private Category category;
}
