package com.dogsearch.demo.impl;

import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.model.Role;
import com.dogsearch.demo.repository.PersonRepo;
import com.dogsearch.demo.repository.RoleRepo;
import com.dogsearch.demo.util.exception.UtilException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepo roleRepo;
    @Mock
    private PersonRepo personRepo;
    private RoleServiceImpl roleService;
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        personService = new PersonServiceImpl(personRepo);
        roleService = new RoleServiceImpl(roleRepo, personService);
    }

    @Test
    void itShouldSaveRole() throws Exception {
        //given
        Role roleToSave = new Role("Admin");

        //when
        roleService.save(roleToSave);

        //then
        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepo).save(roleArgumentCaptor.capture());
        Role capturedRole = roleArgumentCaptor.getValue();

        assertThat(roleToSave).isEqualTo(capturedRole);
    }

    @Test
    void itShouldNotSaveRoleBecauseDoenstHaveAllParams() throws Exception {
        //given
        Role roleToSave = new Role("");
        List<String> exceptionMessageParams = new ArrayList<>(Arrays.asList(Role.objectNamePtBr));

        //when
        //then
        assertThatThrownBy(() -> roleService.save(roleToSave))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(UtilException.exceptionMessageBuilder(
                        UtilException.PARAMS_DONT_FILLED_TO_THE_CLASS_WITH_PARAM,
                        exceptionMessageParams
                ));
    }

    @Test
    void itShouldAddRoleOnPerson() throws Exception {
        //given
        Person personToSave = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "18 997 556"
        );
        Role roleToSave = new Role("Client");
        roleToSave.setId(11L); // Simulating that role has been saved and returned with id

        //when
        roleService.addRoleAndSavePerson(personToSave, roleToSave);

        //then
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personRepo).save(personArgumentCaptor.capture());
        Person personCaptured = personArgumentCaptor.getValue();

        assertThat(personCaptured.getRoles()).isEqualTo(personToSave.getRoles());
    }

    @Test
    void itShouldAddMultipleRolesOnPerson() throws Exception {
        //given
        Person personToSave = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "18 997 556"
        );
        Role userRole = new Role("User");
        Role adminRole = new Role("Admin");
        userRole.setId(11L);
        adminRole.setId(12L);

        //when
        roleService.addMultipleRoles(personToSave, List.of(userRole, adminRole));

        //then
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personRepo).save(personArgumentCaptor.capture());
        Person personCaptured = personArgumentCaptor.getValue();

        assertThat(personCaptured.getRoles()).isEqualTo(personToSave.getRoles());
    }
}