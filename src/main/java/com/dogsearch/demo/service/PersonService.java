package com.dogsearch.demo.service;

import com.dogsearch.demo.dto.person.PersonDTO;
import com.dogsearch.demo.model.Person;

public interface PersonService {
    Person save(Person person) throws Exception;
    Person delete(Long id) throws Exception;
}
