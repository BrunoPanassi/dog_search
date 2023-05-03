package com.dogsearch.demo.impl;

import com.dogsearch.demo.dto.announcement.AnnouncementDTO;
import com.dogsearch.demo.mapper.announcement.AnnouncementConverter;
import com.dogsearch.demo.model.Announcement;
import com.dogsearch.demo.repository.AnnouncementRepo;
import com.dogsearch.demo.service.AnnouncementService;
import com.dogsearch.demo.util.exception.UtilException;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @AllArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private AnnouncementRepo announcementRepo;
    public static final String[] announcementException = {Announcement.objectNamePtBr};
    @Override
    public Announcement save(Announcement announcement) throws Exception {
        UtilParam.checkIfAllParamsAreFilled(getParams(announcement), Announcement.objectNamePtBr);
        AnnouncementDTO dto = AnnouncementConverter.CONVERTER.getDto(announcement);
        if (!doesHaveAnId(dto) && doesAlreadyExistsInDatabase(announcement))
            UtilException.throwWithMessageBuilder(UtilException.ALREADY_EXISTS_WITH_PARAM, announcementException);
        return announcementRepo.save(announcement);
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

    public boolean doesHaveAnId(AnnouncementDTO announcement) {
        return announcement.getId() != null;
    }

    public boolean doesAlreadyExistsInDatabase(Announcement announcement) {
        AnnouncementDTO announcementFound = find(announcement.getPerson().getName(), announcement.getTitle());
        return announcementFound != null;
    }

    @Override
    public AnnouncementDTO find(String personName, String title) {
        return announcementRepo.find(personName, title);
    }

    @Override
    public void delete(Announcement announcement) throws Exception {
        AnnouncementDTO announcementFound = find(announcement.getPerson().getName(), announcement.getTitle());
        if (announcementFound == null || !doesHaveAnId(announcementFound))
            UtilException.throwWithMessageBuilder(UtilException.DONT_EXISTS_WITH_PARAM, announcementException);
        announcementRepo.deleteById(announcementFound.getId());
    }
}
