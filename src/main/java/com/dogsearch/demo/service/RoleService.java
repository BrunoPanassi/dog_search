package com.dogsearch.demo.service;

import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.model.Role;

import java.util.List;

public interface RoleService {
    Role save(Role role) throws Exception;
    List<Role> find(Long id, String description) throws Exception;
    Role delete(Long id) throws Exception;
    Person addRole(Person person, Role role) throws Exception;
    void addRoleAndSavePerson(Person person, Role role) throws Exception;
    void addMultipleRoles(Person person, List<Role> roles) throws Exception;
}
