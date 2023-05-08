package com.dogsearch.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Data
@Getter @Setter
@RequiredArgsConstructor @NoArgsConstructor(force = true)
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

    public SubCategory(Long id, @NonNull String name, @NonNull Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
}
