package com.dogsearch.demo.impl;

import com.dogsearch.demo.dto.person.PersonDTO;
import com.dogsearch.demo.mapper.person.PersonConverter;
import com.dogsearch.demo.model.Announcement;
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
import java.util.Optional;

@Service @AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepo personRepo;
    public static final String[] personException = {Person.objectNamePtBr};
    public static final String[] personAnnouncementException = {Person.objectNamePtBr, Announcement.objectNamePtBr};
    public static final String[] phoneNumber = {"Número de telefone"};

    @Override
    public Person save(Person person) throws Exception {
        UtilParam.checkIfAllParamsAreFilled(getParams(person), Person.objectNamePtBr);
        verifyIfDoensHaveAndIdButAlreadyExistsInDatabase(person);
        verifyIfHaveAnIdButDoensExistsInDatabase(person);
        return personRepo.save(person);
    }

    public void verifyIfDoensHaveAndIdButAlreadyExistsInDatabase(Person person) throws Exception {
        if (!doesHaveAnId(person) && doesAlreadyExistsInDatabaseByPhoneNumber(person))
            UtilException.throwWithMessageBuilder(UtilException.ALREADY_REGISTERED_WITH_PARAM, phoneNumber);
    }

    private void verifyIfHaveAnIdButDoensExistsInDatabase(Person person) throws Exception {
        if (doesHaveAnId(person) && !doesAlreadyExistsInDatabaseById(person))
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, personException);
    }

    public List<PersonDTO> find(Long id, String name) throws Exception {
        if (id == null || name == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_DONT_FILLED, personException);
        return ifCannotFindThrowEitherReturnCategoryBy(id, name);
    }

    public List<Announcement> findAnnouncementsByPersonId(Long id) throws Exception {
        List<Announcement> announcements = personRepo.findAnnouncementsByPersonId(id);
        if (announcements == null || announcements.size() < 1)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_DO_NOT_HAVE_PARAM, personAnnouncementException);
        return announcements;
    }

    public List<PersonDTO> ifCannotFindThrowEitherReturnCategoryBy(Long id, String name) throws Exception {
        List<PersonDTO> personFounded = personRepo.findByIdAndName(id, name);
        if (personFounded == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_NOT_FOUND, personException);
        return personFounded;
    }

    @Override
    public Person delete(Long id) throws Exception {
        Optional<Person> personFounded = personRepo.findById(id);
        if (!personFounded.isPresent())
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, personException);
        personRepo.deleteById(id);
        return personFounded.get();
    }

    public boolean doesAlreadyExistsInDatabaseByPhoneNumber(Person person) {
        List<PersonDTO> personFinded = personRepo.findPersonNameByPhoneNumber(person.getPhoneNumber());
        return personFinded.size() > 0;
    }

    public boolean doesAlreadyExistsInDatabaseById(Person person) {
        Optional<Person> personFinded = personRepo.findById(person.getId());
        return personFinded.isPresent();
    }

    public Person verifyIfExists(Long id) throws Exception {
        Optional<Person> personFounded = personRepo.findById(id);
        if (!personFounded.isPresent())
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, personException);
        return personFounded.get();
    }

    public boolean doesHaveAnId(Person person) {
        return person.getId() != null;
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
