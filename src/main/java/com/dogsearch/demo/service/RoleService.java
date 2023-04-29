package com.dogsearch.demo.service;

import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.model.Role;

import java.util.List;

public interface RoleService {
    Role save(Role role) throws Exception;
    Role find(String description) throws Exception;
    void delete(Role role) throws Exception;
    Person addRole(Person person, Role role) throws Exception;
    void addRoleAndSavePerson(Person person, Role role) throws Exception;
    void addMultipleRoles(Person person, List<Role> roles) throws Exception;
}
