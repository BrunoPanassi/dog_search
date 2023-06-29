package com.dogsearch.demo.model;

import com.dogsearch.demo.dto.person.PersonSaveDTO;
import com.dogsearch.demo.util.param.UtilParam;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity @Data
@Getter @Setter
@RequiredArgsConstructor @NoArgsConstructor(force = true)
public class Person implements UserDetails {

    public static final String objectNamePtBr = "Pessoa";

    @Id
    @SequenceGenerator(name = "seq_person", sequenceName = "seq_person")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_person")
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String password;
    @NonNull
    private String city;
    @NonNull
    private String neighbourhood;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "person") @JsonManagedReference
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

    public Person(@NonNull String name, String password, @NonNull String city, @NonNull String neighbourhood, @NonNull String phoneNumber, @NonNull String email, Collection<Role> roles) {
        this.name = name;
        this.password = password;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getDescription())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
