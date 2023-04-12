package com.dogsearch.demo.impl;

import com.dogsearch.demo.dto.person.PersonDTO;
import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.repository.PersonRepo;
import com.dogsearch.demo.service.PersonService;
import com.dogsearch.demo.util.exception.UtilException;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepo personRepo;

    @Override
    public Person save(Person person) throws Exception {
        UtilParam.checkIfAllParamsAreFilled(getPersonParams(person));
        if (!doesPersonHaveAnId(person) && doesPersonAlreadyExistsInDatabase(person))
            UtilException.throwDefault(UtilException.USER_ALREADY_EXISTS);
        return personRepo.save(person);
    }

    @Override
    public PersonDTO findIdAndName(String name, String phoneNumber) throws Exception {
        return personRepo.findPersonNameByNameAndPhoneNumber(name, phoneNumber);
    }

    @Override
    public void delete(Person person) throws Exception {
        PersonDTO personFinded = findIdAndName(person.getName(), person.getPhoneNumber());
        if (personFinded.getId() == null)
            UtilException.throwDefault(UtilException.USER_DONT_EXISTS);
        personRepo.deleteById(personFinded.getId());
    }

    public boolean doesPersonHaveAnId(Person person) {
        return person.getId() != null;
    }

    public boolean doesPersonAlreadyExistsInDatabase(Person person) throws Exception {
        PersonDTO personFinded = findIdAndName(person.getName(), person.getPhoneNumber());
        return personFinded != null && personFinded.getId() != null;
    }

    public static String[] getPersonParams(Person person) {
        String[] params = {
                person.getName().toString(),
                person.getPhoneNumber().toString(),
                person.getCity().toString(),
                person.getNeighbourhood().toString()
        };
        return params;
    }
}
