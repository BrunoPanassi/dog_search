package com.dogsearch.demo.mapper.person;

import com.dogsearch.demo.dto.person.PersonDTO;
import com.dogsearch.demo.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonConverter {
    PersonConverter CONVERTER = Mappers.getMapper(PersonConverter.class);

    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.name", target = "name")
    Person getEntity(PersonDTO dto);

    @Mapping(source = "person.id", target = "id")
    @Mapping(source = "person.name", target = "name")
    PersonDTO getDto(Person person);
}
