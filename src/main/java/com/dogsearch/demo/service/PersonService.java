package com.dogsearch.demo.service;

import com.dogsearch.demo.dto.person.PersonDTO;
import com.dogsearch.demo.model.Person;

public interface PersonService {
    Person save(Person person) throws Exception;
    PersonDTO findIdAndName(String name, String phoneNumber) throws Exception;
    void delete(Person person) throws Exception;
}
