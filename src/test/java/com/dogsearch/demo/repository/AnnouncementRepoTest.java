package com.dogsearch.demo.repository;

import com.dogsearch.demo.dto.announcement.AnnouncementDTO;
import com.dogsearch.demo.model.Announcement;
import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.model.SubCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;
@DataJpaTest
class AnnouncementRepoTest {

    @Autowired
    AnnouncementRepo announcementRepo;
    @Autowired
    PersonRepo personRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    SubCategoryRepo subCategoryRepo;
    @AfterEach
    void tearDown() {
        announcementRepo.deleteAll();
        personRepo.deleteAll();
        categoryRepo.deleteAll();
        subCategoryRepo.deleteAll();
    }

    @Test
    @Disabled
    void itShouldFindAnnouncement() {
        //given
        Person person = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "(18) 9976 555"
        );
        Category cao = new Category("Cão");
        SubCategory caoGuia = new SubCategory("Cão Guia", cao);
        Announcement announcementToSave = new Announcement(
                person,
                "Pastor Alemão",
                caoGuia,
                "4 anos de idade, usado como cão de guarda"
        );
        personRepo.save(person);
        categoryRepo.save(cao);
        subCategoryRepo.save(caoGuia);

        //when
        announcementRepo.save(announcementToSave);

        //then
//        AnnouncementDTO annoucementFound = announcementRepo.find(person.getName(), announcementToSave.getTitle());
//        assertThat(annoucementFound.getPersonName()).isEqualTo(person.getName());
    }

    @Test
    @Disabled
    void itShouldNotFindAnnouncement() {
        //given
        Person person = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "(18) 9976 555"
        );
        Category cao = new Category("Cão");
        SubCategory caoGuia = new SubCategory("Cão Guia", cao);
        Announcement announcementToSave = new Announcement(
                person,
                "Pastor Alemão",
                caoGuia,
                "4 anos de idade, usado como cão de guarda"
        );
        String wrongTitle = "pastor alemao";
        personRepo.save(person);
        categoryRepo.save(cao);
        subCategoryRepo.save(caoGuia);

        //when
        announcementRepo.save(announcementToSave);

        //then
//        AnnouncementDTO annoucementFound = announcementRepo.find(person.getName(), wrongTitle);
//        assertThat(annoucementFound).isNull();
    }
}