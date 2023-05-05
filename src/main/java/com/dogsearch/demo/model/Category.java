package com.dogsearch.demo.model;

import com.dogsearch.demo.dto.category.CategoryCreateDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data
@Getter @Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Category {

    public static String objectNamePtBr = "Categoria";

    @Id
    @SequenceGenerator(name = "seq_category", sequenceName = "seq_category")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_category")
    private Long id;
    @NonNull
    private String name;

    public Category(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }
}
