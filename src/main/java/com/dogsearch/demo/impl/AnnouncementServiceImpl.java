package com.dogsearch.demo.impl;

import com.dogsearch.demo.dto.announcement.AnnouncementDTO;
import com.dogsearch.demo.dto.announcement.AnnouncementSaveDTO;
import com.dogsearch.demo.dto.announcement.AnnouncementTitlePersonDTO;
import com.dogsearch.demo.mapper.announcement.AnnouncementConverter;
import com.dogsearch.demo.model.Announcement;
import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.model.SubCategory;
import com.dogsearch.demo.repository.AnnouncementRepo;
import com.dogsearch.demo.service.AnnouncementService;
import com.dogsearch.demo.util.exception.UtilException;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepo announcementRepo;
    @NonNull
    private PersonServiceImpl personService;
    @NonNull
    private SubCategoryServiceImpl subCategoryService;
    public static final String[] announcementException = {Announcement.objectNamePtBr};

    @Override
    public Announcement save(Announcement announcement) throws Exception {
        UtilParam.checkIfAllParamsAreFilled(getParams(announcement), Announcement.objectNamePtBr);
        verifyIfDoensHaveAndIdButAlreadyExistsInDatabase(announcement);
        verifyIfHaveAnIdButDoensExistsInDatabase(announcement);
        return announcementRepo.save(announcement);
    }

    public void verifyIfDoensHaveAndIdButAlreadyExistsInDatabase(Announcement announcement) throws Exception {
        if (!doesHaveAnId(announcement) && doesAlreadyExistsInDatabaseByName(announcement))
            UtilException.throwWithMessageBuilder(UtilException.ALREADY_EXISTS_WITH_PARAM, announcementException);
    }

    private void verifyIfHaveAnIdButDoensExistsInDatabase(Announcement announcement) throws Exception {
        if (doesHaveAnId(announcement) && !doesAlreadyExistsInDatabaseById(announcement))
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, announcementException);
    }

    public static List<String> getParams(Announcement announcement) throws Exception {
        List<String> params = new ArrayList<>();
        try {
            params.addAll(List.of(
                    announcement.getTitle().toString(),
                    announcement.getText().toString(),
                    announcement.getSubCategory().getName(),
                    announcement.getPerson().getName())
            );
        } catch (Exception e) {
            UtilParam.throwAllParamsAreNotFilled(Announcement.objectNamePtBr);
        }
        return params;
    }

    public boolean doesHaveAnId(Announcement announcement) {
        return announcement.getId() != null;
    }

    public boolean doesAlreadyExistsInDatabaseByName(Announcement announcement) {
        List<AnnouncementDTO> announcementFounded = announcementRepo.find(announcement.getPerson().getName(), announcement.getTitle());
        return announcementFounded.size() > 0;
    }

    public boolean doesAlreadyExistsInDatabaseById(Announcement announcement) {
        Optional<Announcement> announcementFounded = announcementRepo.findById(announcement.getId());
        return announcementFounded.isPresent();
    }

    @Override
    public List<AnnouncementDTO> find(Long personId, String title) throws Exception {
        if (personId == null || title == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_DONT_FILLED, announcementException);
        return ifCannotFindThrowEitherReturnAnnouncementBy(personId, title);
    }

    public List<AnnouncementDTO> ifCannotFindThrowEitherReturnAnnouncementBy(Long personId, String title) throws Exception {
        List<AnnouncementDTO> announcementFounded = announcementRepo.findByTitleAndPersonId(personId, title);
        if (announcementFounded == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_NOT_FOUND, announcementException);
        return announcementFounded;
    }

    public Announcement createAnnouncementFromSaveDTO(AnnouncementSaveDTO dto, Long id) throws Exception {
        Person person = personService.verifyIfExists(dto.getPersonId());
        SubCategory subCategory = subCategoryService.verifyIfExists(dto.getSubCategoryId());
        if (id == UtilParam.DEFAULT_LONG_PARAM_TO_REPO)
            id = null;
        return new Announcement(
                id,
                person,
                dto.getTitle(),
                subCategory,
                dto.getText(),
                dto.getImages()
        );
    }

    @Override
    public Announcement delete(Long id) throws Exception {
        Optional<Announcement> announcementFounded = announcementRepo.findById(id);
        if (!announcementFounded.isPresent())
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, announcementException);
        announcementRepo.deleteById(id);
        return announcementFounded.get();
    }
}
