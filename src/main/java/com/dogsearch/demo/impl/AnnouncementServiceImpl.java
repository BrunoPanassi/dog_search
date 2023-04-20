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

import java.util.List;

@Service @AllArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private AnnouncementRepo announcementRepo;
    @Override
    public Announcement save(Announcement announcement) throws Exception {
        UtilParam.checkIfAllParamsAreFilled(getParams(announcement), Announcement.objectNamePtBr);
        AnnouncementDTO dto = AnnouncementConverter.CONVERTER.getDto(announcement);
        if (!doesHaveAnId(dto) && doesPersonAlreadyExistsInDatabase(announcement))
            UtilException.throwDefault(UtilException.USER_ALREADY_EXISTS);
        return announcementRepo.save(announcement);
    }

    public static String[] getParams(Announcement announcement) {
        String[] params = {
                announcement.getTitle().toString(),
                announcement.getText().toString(),
                announcement.getCategory().getName(),
                announcement.getPerson().getName()
        };
        return params;
    }

    public boolean doesHaveAnId(AnnouncementDTO announcement) {
        return announcement.getId() != null;
    }

    public boolean doesPersonAlreadyExistsInDatabase(Announcement announcement) {
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
        if (!doesHaveAnId(announcementFound)) {
            UtilException.throwDefault(
                    UtilException.exceptionMessageBuilder(
                            UtilException.DONT_EXISTS_WITH_PARAM,
                            List.of(Announcement.objectNamePtBr)
                    )
            );
        }
        announcementRepo.deleteById(announcementFound.getId());
    }
}
