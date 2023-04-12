package com.dogsearch.demo.repository;

import com.dogsearch.demo.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
class PersonRepoTest {

    @Autowired
    PersonRepo personRepo;

    @AfterEach
    void tearDown() {
        personRepo.deleteAll();
    }

    @Test
    void itShouldSaveUSer() {
        //given
        String name = "Bruno Henrique";
        String phoneNumber = "(18) 99777 6666";
        Person personToSave = new Person(
                name,
                "Araçatuba",
                "Concordia",
                phoneNumber
        );
        personRepo.save(personToSave);

        //when
        String personFinded = personRepo.findClientNameByNameAndPhoneNumber(name, phoneNumber);

        //then
        assertThat(personFinded).isEqualTo(personToSave.getName());
    }
}