package com.dogsearch.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "user") @Getter @Setter @AllArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name = "seq_user", sequenceName = "seq_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_user")
    private Long id;
    private String name;
    private String city;
    private String neighbourhood;
    private String phoneNumber;
    @ManyToMany
    private List<Role> roles;
    @OneToMany(mappedBy = "user")
    private List<Announcement> announcements;
}
