package com.dogsearch.demo.controller;

import com.dogsearch.demo.dto.announcement.AnnouncementCitySubCategoryDTO;
import com.dogsearch.demo.dto.announcement.AnnouncementSaveDTO;
import com.dogsearch.demo.impl.AnnouncementServiceImpl;
import com.dogsearch.demo.model.Announcement;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/announcement")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AnnouncementControlller { // TODO: Trocar para somente dois L
    @Autowired
    private final AnnouncementServiceImpl announcementService;

    @GetMapping()
    public ResponseEntity get(@RequestParam(name = "id", defaultValue = "0") Long id,
                              @RequestParam(name = "title", defaultValue = UtilParam.DEFAULT_STRING_PARAM_TO_REPO, required = false) String title) {
        try {
            return ResponseEntity.ok().body(announcementService.find(id, title));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PostMapping(value = "/save", consumes = "multipart/form-data")
    public ResponseEntity save(@ModelAttribute AnnouncementSaveDTO announcementBody) {
        try {
            Announcement announcement = announcementService.createAnnouncementFromSaveDTO(announcementBody, UtilParam.DEFAULT_LONG_PARAM_TO_REPO, announcementBody.getImages());
            UtilParam.checkIfAllParamsAreFilled(announcementService.getParams(announcement), Announcement.objectNamePtBr);
            URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/announcement/save").toUriString());
            return ResponseEntity.created(location).body(announcementService.save(announcement));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PutMapping(value="/save/{id}", consumes = "multipart/form-data")
    public ResponseEntity update(@ModelAttribute AnnouncementSaveDTO announcementBody, @PathVariable("id") Long id) {
        try {
            Announcement announcement = announcementService.createAnnouncementFromSaveDTO(announcementBody, id, announcementBody.getImages());
            UtilParam.checkIfAllParamsAreFilled(announcementService.getParams(announcement), Announcement.objectNamePtBr);
            URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/announcement/save/".concat(String.valueOf(id))).toUriString());
            return ResponseEntity.created(location).body(announcementService.save(announcement));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(announcementService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @GetMapping("/cities")
    public ResponseEntity getCities() {
        try {
            return ResponseEntity.ok().body(announcementService.getCities());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PostMapping("/city-and-sub-category")
    public ResponseEntity getByCityAndSubCategory(@RequestBody AnnouncementCitySubCategoryDTO dto, @PageableDefault(page = 0, size = 8) Pageable pageable) {
        try {
            return ResponseEntity.ok().body(announcementService.getByCityAndSubCategory(dto.getCity(), dto.getSubCategory(), pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity getByUser(@PathVariable("email") String email, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        try {
            return ResponseEntity.ok().body(announcementService.getByUser(email, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }
}
