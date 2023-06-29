package com.dogsearch.demo.repository;

import com.dogsearch.demo.dto.role.PersonRoleDTO;
import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.model.Role;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
class RoleRepoTest {

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PersonRepo personRepo;

    @AfterEach
    void tearDown() {
        roleRepo.deleteAll();
    }

    @Test
    @Disabled
    void itShouldSaveRole() {
        //given
        Role role = new Role("USER");

        //when
        roleRepo.save(role);

        //then
//        Role roleFounded = roleRepo.find("user");
//        assertThat(roleFounded).isEqualTo(role);
    }

    @Test
    void itShouldSaveRoleForAPerson() {
        //given
        Role role = new Role("ADMIN");
        Person person = new Person(
                "Bruno Henrique",
                "Aracatuba",
                "Concordia",
                "18 997 556"
        );
        person.setRoles(List.of(role));

        //when
        roleRepo.save(role);
        personRepo.save(person);

        //then
        PersonRoleDTO personFounded = roleRepo.findPersonByRoleDescription("admin");
        assertThat(personFounded.getRole()).isEqualTo(role.getDescription());
    }
}