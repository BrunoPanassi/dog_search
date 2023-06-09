package com.dogsearch.demo.service;

import com.dogsearch.demo.dto.announcement.AnnouncementDTO;
import com.dogsearch.demo.dto.announcement.AnnouncementTitlePersonDTO;
import com.dogsearch.demo.model.Announcement;

import java.util.List;

public interface AnnouncementService {
    Announcement save(Announcement announcement) throws Exception;
    List<AnnouncementDTO> find(Long personId, String title) throws Exception;
    Announcement delete(Long id) throws Exception;
}
