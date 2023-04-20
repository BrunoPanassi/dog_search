package com.dogsearch.demo.impl;

import com.dogsearch.demo.model.Announcement;
import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.repository.AnnouncementRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnnouncementServiceImplTest {

    @Mock
    AnnouncementRepo announcementRepo;
    AnnouncementServiceImpl announcementService;

    @BeforeEach
    void setUp() {
        announcementService = new AnnouncementServiceImpl(announcementRepo);
    }

    @Test
    void itShouldSaveAnnouncement() throws Exception {
        //given
        Person personToSave = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "(18) 9967 555"
        );
        Category caoGuia = new Category("Cão Guia");
        Announcement announcementToSave = new Announcement(
                personToSave,
                "Cão guia",
                caoGuia,
                "Cão guia especializado para"
        );

        //when
        announcementService.save(announcementToSave);

        //then
        ArgumentCaptor<Announcement> announcementArgumentCaptor = ArgumentCaptor.forClass(Announcement.class);
        verify(announcementRepo).save(announcementArgumentCaptor.capture());
        Announcement capturedAnnouncement = announcementArgumentCaptor.getValue();

        assertThat(capturedAnnouncement).isEqualTo(announcementToSave);
    }

    @Test
    void itShouldNotSaveBecauseAllParamsAreNotFilled() {
    }

    @Test
    void itShouldNotSaveBecauseAnnouncementTitleToTheSamePersonAlreadyExists() {
    }

    @Test
    void itShouldDelete() {
    }
}