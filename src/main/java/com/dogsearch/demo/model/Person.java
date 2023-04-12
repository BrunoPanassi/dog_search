package com.dogsearch.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity @Data
@Getter @Setter
public class Person {

    @Id
    @SequenceGenerator(name = "seq_person", sequenceName = "seq_person")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_person")
    private Long id;

    private String name;
    private String city;
    private String neighbourhood;
    private String phoneNumber;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "person")
    private List<Announcement> announcements;

    public Person(String name, String city, String neighbourhood, String phoneNumber) {
        this.name = name;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.phoneNumber = phoneNumber;
    }
}
