package com.dogsearch.demo.impl;

import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.model.Role;
import com.dogsearch.demo.repository.RoleRepo;
import com.dogsearch.demo.service.RoleService;
import com.dogsearch.demo.util.exception.UtilException;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    @NonNull
    private RoleRepo roleRepo;
    @NonNull
    private PersonServiceImpl personService;

    public static final String[] roleException = {Role.objectNamePtBr};
    public static final String[] personRoleException = {Person.objectNamePtBr, Role.objectNamePtBr};

    @Override
    public Role save(Role role) throws Exception {
        UtilParam.checkIfAllParamsAreFilled(getParams(role), Role.objectNamePtBr);
        if (!doesHaveAnId(role) && doesAlreadyExistsInDatabase(role))
            UtilException.throwWithMessageBuilder(UtilException.ALREADY_EXISTS_WITH_PARAM, roleException);
        return roleRepo.save(role);
    }

    public static List<String> getParams(Role role) throws Exception {
        List<String> params = new ArrayList<>();
        try {
            params.addAll(List.of(role.getDescription().toString())
            );
        } catch (Exception e) {
            UtilParam.throwAllParamsAreNotFilled(Role.objectNamePtBr);
        }
        return params;
    }

    private boolean doesHaveAnId(Role role) {
        return role.getId() != null;
    }

    private boolean doesAlreadyExistsInDatabase(Role role) throws Exception {
        Role roleFounded = find(role.getDescription());
        return roleFounded != null;
    }

    @Override
    public Person addRole(Person person, Role role) throws Exception {
        verifyIfPersonAlreadyHasThisRole(person, role);
        person.getRoles().add(role);
        return person;
    }

    @Override
    public void addRoleAndSavePerson(Person person, Role role) throws Exception {
        person = addRole(person, role);
        personService.save(person);
    }

    @Override
    public void addMultipleRoles(Person person, List<Role> roles) throws Exception {
        for (Role eachRole : roles) {
            person = addRole(person, eachRole);
        }
        personService.save(person);
    }

    private void verifyIfPersonAlreadyHasThisRole(Person person, Role role) throws Exception {
        if (doesPersonHasThisRole(person, role)) {
            String[] exceptionParams = {Person.objectNamePtBr, Role.objectNamePtBr};
            UtilException.throwWithMessageBuilder(UtilException.THIS_PARAM_ALREADY_HAVE_THIS_PARAM, personRoleException);
        }
        if (!doesHaveAnId(role)) {
            UtilException.throwWithMessageBuilder(UtilException.PARAM_NOT_FOUND, roleException);
        }
    }

    private boolean doesPersonHasThisRole(Person person, Role roleToAdd) {
        return person.getRoles().stream().anyMatch(role -> role.getId() == roleToAdd.getId());
    }

    @Override
    public Role find(String description) throws Exception {
        return roleRepo.find(description);
    }

    @Override
    public void delete(Role role) throws Exception {
        Role roleFounded = find(role.getDescription());
        if (roleFounded == null || !doesHaveAnId(roleFounded)) {
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, roleException);
        }
        roleRepo.deleteById(role.getId());
    }
}
