package com.dogsearch.demo.model;

import com.dogsearch.demo.dto.person.PersonSaveDTO;
import com.dogsearch.demo.util.param.UtilParam;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity @Data
@Getter @Setter
@RequiredArgsConstructor @NoArgsConstructor(force = true)
public class Person {

    public static final String objectNamePtBr = "Pessoa";

    @Id
    @SequenceGenerator(name = "seq_person", sequenceName = "seq_person")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_person")
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String city;
    @NonNull
    private String neighbourhood;
    @NonNull
    private String phoneNumber;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "person")
    private List<Announcement> announcements;

    public Person (PersonSaveDTO personDTO, Long id) {
        this.name = personDTO.getName();
        this.city = personDTO.getCity();
        this.neighbourhood = personDTO.getNeighbourhood();
        this.phoneNumber = personDTO.getPhoneNumber();
        if (id > UtilParam.DEFAULT_LONG_PARAM_TO_REPO) {
            this.id = id;
        }
    }
}
