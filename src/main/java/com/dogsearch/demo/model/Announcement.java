package com.dogsearch.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Data
@Getter @Setter @AllArgsConstructor
public class Announcement {

    @Id
    @SequenceGenerator(name = "seq_announcement", sequenceName = "seq_announcement")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_announcement")
    private Long id;
    @ManyToOne @JoinColumn(name = "person_id")
    private Person person;
    private String title;
    @ManyToOne @JoinColumn(name = "category_id")
    private Category category;
    private String text;
    private List<String> images;
}
