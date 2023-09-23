package com.dogsearch.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Data
@Getter @Setter @RequiredArgsConstructor @NoArgsConstructor
public class Announcement {

    public static final String objectNamePtBr = "Anúncio";

    @Id
    @SequenceGenerator(name = "seq_announcement", sequenceName = "seq_announcement")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_announcement")
    private Long id;
    @NonNull @ManyToOne @JoinColumn(name = "person_id") @JsonBackReference
    private Person person;
    @NonNull
    private String title;
    @NonNull @ManyToOne @JoinColumn(name = "sub-category_id")
    private SubCategory subCategory;
    @NonNull
    private String text;

    @OneToOne(mappedBy = "announcement")
    private Image image;

    public Announcement(Long id, Person person, String title, SubCategory subCategory, String text) {
        this.id = id;
        this.person = person;
        this.title = title;
        this.subCategory = subCategory;
        this.text = text;
    }
}
