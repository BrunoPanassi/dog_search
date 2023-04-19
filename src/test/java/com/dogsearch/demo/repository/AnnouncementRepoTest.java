package com.dogsearch.demo.repository;

import com.dogsearch.demo.dto.person.AnnouncementDTO;
import com.dogsearch.demo.model.Announcement;
import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
@DataJpaTest
class AnnouncementRepoTest {

    @Autowired
    AnnouncementRepo announcementRepo;
    @Autowired
    PersonRepo personRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @AfterEach
    void tearDown() {
        announcementRepo.deleteAll();
    }

    @Test
    void itShouldFindAnnouncement() {
        //given
        Person person = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "(18) 9976 555"
        );
        Category category = new Category("Pastor Alemão");
        Announcement announcementToSave = new Announcement(
                person,
                "Pastor Alemão",
                category,
                "4 anos de idade, usado como cão de guarda"
        );
        personRepo.save(person);
        categoryRepo.save(category);

        //when
        announcementRepo.save(announcementToSave);

        //then
        AnnouncementDTO annoucementFound = announcementRepo.findByPersonNameAndTitle(person.getName(), announcementToSave.getTitle());
        assertThat(annoucementFound.getPersonName()).isEqualTo(person.getName());
    }

    @Test
    void itShouldNotFindAnnouncement() {
        //given
        Person person = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "(18) 9976 555"
        );
        Category category = new Category("Pastor Alemão");
        Announcement announcementToSave = new Announcement(
                person,
                "Pastor Alemão",
                category,
                "4 anos de idade, usado como cão de guarda"
        );
        String wrongTitle = "pastor alemao";
        personRepo.save(person);
        categoryRepo.save(category);

        //when
        announcementRepo.save(announcementToSave);

        //then
        AnnouncementDTO annoucementFound = announcementRepo.findByPersonNameAndTitle(person.getName(), wrongTitle);
        assertThat(annoucementFound).isNull();
    }
}