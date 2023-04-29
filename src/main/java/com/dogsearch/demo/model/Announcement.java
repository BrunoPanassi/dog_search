package com.dogsearch.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Data
@Getter @Setter @RequiredArgsConstructor
public class Announcement {

    public static final String objectNamePtBr = "Anúncio";

    @Id
    @SequenceGenerator(name = "seq_announcement", sequenceName = "seq_announcement")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_announcement")
    private Long id;
    @NonNull @ManyToOne @JoinColumn(name = "person_id")
    private Person person;
    @NonNull
    private String title;
    @NonNull @ManyToOne @JoinColumn(name = "category_id")
    private Category category;
    @NonNull
    private String text;
    private List<String> images; //TODO: Change to Blob and test
}
