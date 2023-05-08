package com.dogsearch.demo.model;

import jakarta.persistence.*;
import lombok.*;

import javax.management.ConstructorParameters;

@Entity @Data
@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Role {

    public static String objectNamePtBr = "Cargo";
    @Id
    @SequenceGenerator(name = "seq_role", sequenceName = "seq_role")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_role")
    private Long id;

    @NonNull
    private String description;
}
