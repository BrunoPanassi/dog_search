package com.dogsearch.demo.impl;

import com.dogsearch.demo.dto.person.PersonDTO;
import com.dogsearch.demo.mapper.person.PersonConverter;
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
        UtilParam.checkIfAllParamsAreFilled(getParams(person), Person.objectNamePtBr);
        PersonDTO personDTO = PersonConverter.CONVERTER.getDto(person);
        if (!doesHaveAnId(personDTO) && doesPersonAlreadyExistsInDatabase(person))
            UtilException.throwDefault(UtilException.exceptionMessageBuilder(
                    UtilException.ALREADY_EXISTS_WITH_PARAM,
                    List.of(Person.objectNamePtBr)
            ));
        return personRepo.save(person);
    }

    @Override
    public PersonDTO findIdAndName(String name, String phoneNumber) throws Exception {
        return personRepo.findPersonNameByNameAndPhoneNumber(name, phoneNumber);
    }

    @Override
    public void delete(Person person) throws Exception {
        PersonDTO personFinded = findIdAndName(person.getName(), person.getPhoneNumber());
        if (!doesHaveAnId(personFinded)) {
            UtilException.throwDefault(
                    UtilException.exceptionMessageBuilder(
                            UtilException.DONT_EXISTS_WITH_PARAM,
                            List.of(Person.objectNamePtBr)
                    )
            );
        }
        personRepo.deleteById(personFinded.getId());
    }

    public boolean doesHaveAnId(PersonDTO person) {
        return person.getId() != null;
    }

    public boolean doesPersonAlreadyExistsInDatabase(Person person) throws Exception {
        PersonDTO personFinded = findIdAndName(person.getName(), person.getPhoneNumber());
        return personFinded != null;
    }

    public static List<String> getParams(Person person) throws Exception {
        List<String> params = new ArrayList<>();
        try {
            params.addAll(List.of(
                    person.getName().toString(),
                    person.getPhoneNumber().toString(),
                    person.getCity().toString(),
                    person.getNeighbourhood().toString()
            ));
        } catch (Exception e) {
            UtilParam.throwAllParamsAreNotFilled(Person.objectNamePtBr);
        }
        return params;
    }
}
