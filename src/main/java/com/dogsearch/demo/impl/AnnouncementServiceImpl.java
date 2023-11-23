package com.dogsearch.demo.impl;

import com.dogsearch.demo.dto.announcement.AnnouncementDTO;
import com.dogsearch.demo.dto.announcement.AnnouncementSaveDTO;
import com.dogsearch.demo.model.*;
import com.dogsearch.demo.repository.AnnouncementFindRepo;
import com.dogsearch.demo.repository.AnnouncementRepo;
import com.dogsearch.demo.service.AnnouncementService;
import com.dogsearch.demo.util.exception.UtilException;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service @RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepo announcementRepo;
    @Autowired
    private AnnouncementFindRepo announcementFindRepo;
    @NonNull
    private PersonServiceImpl personService;
    @NonNull
    private SubCategoryServiceImpl subCategoryService;
    @Autowired
    private ImageServiceImpl imageService;
    public static final String[] announcementException = {Announcement.objectNamePtBr};

    @Override
    public Announcement save(Announcement announcement) throws Exception {
        UtilParam.checkIfAllParamsAreFilled(getParams(announcement), Announcement.objectNamePtBr);
        verifyIfDoensHaveAndIdButAlreadyExistsInDatabase(announcement);
        verifyIfHaveAnIdButDoensExistsInDatabase(announcement);
        return announcementRepo.save(announcement);
    }

    @Override
    public List<AnnouncementDTO> find(Long personId, String title) throws Exception {
        if (personId == null || title == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_DONT_FILLED, announcementException);
        return ifCannotFindThrowEitherReturnAnnouncementBy(personId, title);
    }

    @Override
    public Announcement delete(Long id) throws Exception {
        Optional<Announcement> announcementFounded = announcementRepo.findById(id);
        if (!announcementFounded.isPresent())
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, announcementException);
        imageService.deleteByAnnouncement(id);
        announcementRepo.deleteById(id);

        return announcementFounded.get();
    }

    public List<String> getCities() {
        return announcementRepo.getCities();
    }

    public List<AnnouncementDTO> getByCityAndSubCategory(String city, String subCategory) throws Exception {
        List<AnnouncementDTO> announcements = announcementRepo.getByCityAndSubCategory(city, subCategory);
        announcements.forEach(announcementDTO -> {
            try {
                Image image = imageService.find(announcementDTO.getId(), UtilParam.DEFAULT_LONG_PARAM_TO_REPO);
                List<byte[]> images = image.getAListOfImages();
                announcementDTO.setImages(images);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        if (announcements == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_NOT_FOUND, announcementException);
        return announcements;
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

    public List<AnnouncementDTO> ifCannotFindThrowEitherReturnAnnouncementBy(Long personId, String title) throws Exception {
        List<AnnouncementDTO> announcementFounded = announcementRepo.findByTitleAndPersonId(personId, title);
        if (announcementFounded == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_NOT_FOUND, announcementException);
        return announcementFounded;
    }

    public Announcement createAnnouncementFromSaveDTO(AnnouncementSaveDTO dto, Long id, MultipartFile[] file) throws Exception {
        Person person = personService.verifyIfExists(dto.getPersonId());
        SubCategory subCategory = subCategoryService.verifyIfExists(dto.getSubCategoryId());

        Stream<byte[]> imagesCompressed = Image.multipartFileArrayToStreamByteArray(file);
        Image image = new Image(imagesCompressed);

        Long imageId = null;
        if (id == UtilParam.DEFAULT_LONG_PARAM_TO_REPO)
            id = null;
        else {
            imageId = imageService.findImageIdByAnnouncementId(id);
        }

        image.setId(imageId);
        Announcement announcement = new Announcement(
                id,
                person,
                dto.getTitle(),
                subCategory,
                dto.getText()
        );
        announcement.setImage(image);
        image.setAnnouncement(announcement);

        imageService.save(image);
        return announcement;
    }

    public Object getByUser(String email, Pageable pageable) throws Exception {
        Page<AnnouncementDTO> announcements = announcementFindRepo.find(email, UtilParam.DEFAULT_STRING_PARAM_TO_REPO, pageable);
        announcements.forEach(announcementDTO -> {
            try {
                Image image = imageService.find(announcementDTO.getId(), UtilParam.DEFAULT_LONG_PARAM_TO_REPO);
                List<byte[]> images = image.getAListOfImages();
                announcementDTO.setImages(images);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        if (announcements == null)
            UtilException.throwWithMessageBuilder(UtilException.PARAM_NOT_FOUND, announcementException);
        return announcements;
    }
}
