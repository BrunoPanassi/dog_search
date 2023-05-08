package com.dogsearch.demo.mapper.person;

import com.dogsearch.demo.dto.person.PersonDTO;
import com.dogsearch.demo.dto.person.PersonSaveDTO;
import com.dogsearch.demo.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonConverter {
    PersonConverter CONVERTER = Mappers.getMapper(PersonConverter.class);

    @Mapping(source = "dto.name", target = "name")
    @Mapping(source = "dto.city", target = "city")
    @Mapping(source = "dto.neighbourhood", target = "neighbourhood")
    @Mapping(source = "dto.phoneNumber", target = "phoneNumber")
    Person getEntity(PersonSaveDTO dto);

    @Mapping(source = "person.id", target = "id")
    @Mapping(source = "person.name", target = "name")
    PersonDTO getDto(Person person);
}
