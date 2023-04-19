package com.dogsearch.demo.service;

import com.dogsearch.demo.dto.person.AnnouncementDTO;
import com.dogsearch.demo.model.Announcement;

public interface AnnouncementService {
    Announcement save(Announcement announcement) throws Exception;
    AnnouncementDTO find(String personName, String title);
    void delete(Announcement announcement) throws Exception;
}
