package com.dogsearch.demo.impl;

import com.dogsearch.demo.model.Image;
import com.dogsearch.demo.repository.ImageRepo;
import com.dogsearch.demo.service.ImageService;
import com.dogsearch.demo.util.param.UtilParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepo imageRepo;
    @Override
    public Image find(Long announcementId, Long imageId) throws Exception {
        if (announcementId == null)
            announcementId = UtilParam.DEFAULT_LONG_PARAM_TO_REPO;
        if (imageId == null)
            announcementId = UtilParam.DEFAULT_LONG_PARAM_TO_REPO;
        return imageRepo.find(announcementId, imageId);
    }

    @Override
    public Long findImageIdByAnnouncementId(Long announcementId) throws Exception {
        Optional<Long> imageId = imageRepo.findImageIdByAnnouncementId(announcementId);
        if (imageId.isPresent())
            return imageId.get();
        return null;
    }

    @Override
    public Image save(Image image) throws Exception {
        return imageRepo.save(image);
    }
}
