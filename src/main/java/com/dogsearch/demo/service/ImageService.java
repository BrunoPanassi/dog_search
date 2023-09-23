package com.dogsearch.demo.service;

import com.dogsearch.demo.model.Image;

import java.util.Optional;

public interface ImageService {
    Image find(Long announcementId, Long imageId) throws Exception;
    Long findImageIdByAnnouncementId(Long announcementId) throws Exception;
    Image save(Image image) throws Exception;
}
