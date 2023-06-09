package com.dogsearch.demo.repository;

import com.dogsearch.demo.dto.person.PersonDTO;
import com.dogsearch.demo.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
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
    @Disabled
    void itShouldSaveAndFindPerson() {
        //given
        String nameToSave = "Bruno Henrique";
        String phoneNumber = "(18) 99777 6666";
        Person personToSave = new Person(
                nameToSave,
                "Araçatuba",
                "Concordia",
                phoneNumber
        );
        personRepo.save(personToSave);

        //when
        String nameToFind = "bruno henrique";
        //PersonDTO personFinded = personRepo.findPersonNameByNameAndPhoneNumber(nameToFind, phoneNumber);

        //then
        //assertThat(personFinded.getName()).isEqualTo(personToSave.getName());
    }

    @Test
    @Disabled
    void itShouldNotFindPerson() {
        //given
        String nameToSave = "Bruno Henrique";
        String phoneNumber = "(18) 99777 6666";
        Person personToSave = new Person(
                nameToSave,
                "Araçatuba",
                "Concordia",
                phoneNumber
        );
        personRepo.save(personToSave);

        //when
        String nameToFind = "bruno henriques";
        //PersonDTO personFinded = personRepo.findPersonNameByNameAndPhoneNumber(nameToFind, phoneNumber);

        //then
        //assertThat(personFinded).isNull();
    }
}