package com.dogsearch.demo.impl;

import com.dogsearch.demo.dto.announcement.AnnouncementDTO;
import com.dogsearch.demo.model.Announcement;
import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.model.SubCategory;
import com.dogsearch.demo.repository.AnnouncementRepo;
import com.dogsearch.demo.util.exception.UtilException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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
        Category cao = new Category("Cão");
        SubCategory caoGuia = new SubCategory("Cão Guia", cao);
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
    @Disabled //TODO: Disabled because NonNull on category change the behavior of the test
    void itShouldNotSaveBecauseAllParamsAreNotFilled() {
        //given
        Person personToSave = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "(18) 9967 555"
        );
        Category cao = new Category("Cão");
        SubCategory caoGuia = new SubCategory("Cão Guia", cao);
        Announcement announcementToSave = new Announcement(
                personToSave,
                "Cão guia",
                caoGuia,
                "Cão guia especializado para"
        );
        announcementToSave.setSubCategory(null);
        List<String> exceptionMessageParams = new ArrayList<>(Arrays.asList(Announcement.objectNamePtBr));

        //when
        //then
        assertThatThrownBy(() -> announcementService.save(announcementToSave))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(UtilException.exceptionMessageBuilder(UtilException.PARAMS_DONT_FILLED_TO_THE_CLASS_WITH_PARAM, exceptionMessageParams));
    }

    @Test
    void itShouldNotSaveBecauseAnnouncementTitleToTheSamePersonAlreadyExists() {
        //given
        Person personToSave = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "(18) 9967 555"
        );
        Category cao = new Category("Cão");
        SubCategory caoGuia = new SubCategory("Cão Guia", cao);
        Announcement announcementToSave = new Announcement(
                personToSave,
                "Cão de Faro",
                caoGuia,
                "Cão de Faro para certas ocasiões"
        );
        List<String> exceptionMessageParams = new ArrayList<>(Arrays.asList(Announcement.objectNamePtBr));

        AnnouncementDTO announcementDTO = new AnnouncementDTO(
                11L,
                announcementToSave.getTitle(),
                announcementToSave.getText(),
                personToSave.getName()
        );

        given(announcementService.find(personToSave.getName(), announcementToSave.getTitle())).willReturn(announcementDTO);

        //when
        //then
        assertThatThrownBy(() -> announcementService.save(announcementToSave))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(UtilException.exceptionMessageBuilder(UtilException.ALREADY_EXISTS_WITH_PARAM, exceptionMessageParams));
    }

    @Test
    void itShouldDelete() throws Exception {
        //given
        Person personToSave = new Person(
                "Bruno Henrique",
                "Araçatuba",
                "Concordia",
                "(18) 9967 555"
        );
        Category cao = new Category("Cão");
        SubCategory caoGuia = new SubCategory("Cão Guia", cao);
        Announcement announcementToSave = new Announcement(
                personToSave,
                "Cão de Faro",
                caoGuia,
                "Cão de Faro para certas ocasiões"
        );
        Long announcementId = 11L;

        AnnouncementDTO announcementDTO = new AnnouncementDTO(
                announcementId,
                announcementToSave.getTitle(),
                announcementToSave.getText(),
                personToSave.getName()
        );

        given(announcementService.find(personToSave.getName(), announcementToSave.getTitle())).willReturn(announcementDTO);

        //when
        announcementService.delete(announcementToSave);

        //then
        verify(announcementRepo).deleteById(announcementId);
    }
}