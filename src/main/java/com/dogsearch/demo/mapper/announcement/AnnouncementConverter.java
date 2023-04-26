package com.dogsearch.demo.mapper.announcement;

import com.dogsearch.demo.dto.announcement.AnnouncementDTO;
import com.dogsearch.demo.model.Announcement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnnouncementConverter {
    AnnouncementConverter CONVERTER = Mappers.getMapper(AnnouncementConverter.class);

    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.title", target = "title")
    @Mapping(source = "dto.text", target = "text")
    @Mapping(source = "dto.personName", target = "person.name")
    Announcement getEntity(AnnouncementDTO dto);

    @Mapping(source = "announcement.id", target = "id")
    @Mapping(source = "announcement.title", target = "title")
    @Mapping(source = "announcement.text", target = "text")
    @Mapping(source = "announcement.person.name", target = "personName")
    AnnouncementDTO getDto(Announcement announcement);
}
