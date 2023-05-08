package com.dogsearch.demo.impl;

import com.dogsearch.demo.model.Category;
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
import java.util.Optional;

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
        verifyIfDoensHaveAndIdButAlreadyExistsInDatabase(role);
        verifyIfHaveAnIdButDoensExistsInDatabase(role);
        return roleRepo.save(role);
    }

    public void verifyIfDoensHaveAndIdButAlreadyExistsInDatabase(Role role) throws Exception {
        if (!doesHaveAnId(role) && doesAlreadyExistsInDatabaseByName(role))
            UtilException.throwWithMessageBuilder(UtilException.ALREADY_EXISTS_WITH_PARAM, roleException);
    }

    private void verifyIfHaveAnIdButDoensExistsInDatabase(Role role) throws Exception {
        if (doesHaveAnId(role) && !doesAlreadyExistsInDatabaseById(role))
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, roleException);
    }

    @Override
    public List<Role> find(Long id, String description) throws Exception {
        if (id == null || description == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_DONT_FILLED, roleException);
        return ifCannotFindThrowEitherReturnRoleBy(id, description);
    }

    public List<Role> ifCannotFindThrowEitherReturnRoleBy(Long id, String name) throws Exception {
        List<Role> roleFounded = roleRepo.findByIdAndName(id, name);
        if (roleFounded == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_NOT_FOUND, roleException);
        return roleFounded;
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
        return role.getId() == null ? false : true;
    }

    private boolean doesAlreadyExistsInDatabaseByName(Role role) throws Exception {
        List<Role> roleFounded = roleRepo.findByDescription(role.getDescription());
        return roleFounded.size() > 0;
    }

    private boolean doesAlreadyExistsInDatabaseById(Role role) throws Exception {
        Optional<Role> roleFounded = roleRepo.findById(role.getId());
        return roleFounded.isPresent();
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
    public Role delete(Long id) throws Exception {
        Optional<Role> roleFounded = roleRepo.findById(id);
        if (!roleFounded.isPresent())
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, roleException);
        roleRepo.deleteById(id);
        return roleFounded.get();
    }
}
