package com.dogsearch.demo.impl;

import com.dogsearch.demo.dto.person.PersonDTO;
import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.repository.PersonRepo;
import com.dogsearch.demo.util.exception.UtilException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepo personRepo;

    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        personService = new PersonServiceImpl(personRepo);
    }

    @Test
    void itShouldSavePerson() throws Exception {
        // given
        Person personToSave = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "18 9976 555"
        );

        // when
        personService.save(personToSave);

        //then
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personRepo).save(personArgumentCaptor.capture());
        Person capturedPerson = personArgumentCaptor.getValue();

        assertThat(capturedPerson).isEqualTo(personToSave);
    }

    @Test
    void itShouldNotSavePersonBecauseAlreadyExists() throws Exception {
        // given
        Long personId = 155L;
        Person personToSave = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "18 9976 555"
        );

        PersonDTO personDTO = new PersonDTO() {
            @Override
            public Long getId() {
                return personId;
            }

            @Override
            public String getName() {
                return personToSave.getName();
            }
        };

        given(personService.findIdAndName(personToSave.getName(), personToSave.getPhoneNumber())).willReturn(personDTO);

        //when
        //then
        assertThatThrownBy(() -> personService.save(personToSave))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(UtilException.USER_ALREADY_EXISTS);

        verify(personRepo, never()).save(any());
    }

    @Test
    void itShouldDeletePerson() throws Exception {
        // given
        Person personToSave = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "18 9976 555"
        );

        Long personId = 10L;
        PersonDTO personDTO = new PersonDTO() {
            @Override
            public Long getId() {
                return personId;
            }

            @Override
            public String getName() {
                return personToSave.getName();
            }
        };
        personService.save(personToSave);
        when(personService.findIdAndName(personToSave.getName(), personToSave.getPhoneNumber())).thenReturn(personDTO);

        // when
        personService.delete(personToSave);

        //then
        verify(personRepo).deleteById(personId);
    }
}