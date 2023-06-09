package com.dogsearch.demo.controller;

import com.dogsearch.demo.dto.announcement.AnnouncementSaveDTO;
import com.dogsearch.demo.impl.AnnouncementServiceImpl;
import com.dogsearch.demo.model.Announcement;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/announcement")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AnnouncementControlller {
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

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody AnnouncementSaveDTO announcementBody) {
        try {
            Announcement announcement = announcementService.createAnnouncementFromSaveDTO(announcementBody, UtilParam.DEFAULT_LONG_PARAM_TO_REPO);
            UtilParam.checkIfAllParamsAreFilled(announcementService.getParams(announcement), Announcement.objectNamePtBr);
            URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/announcement/save").toUriString());
            return ResponseEntity.created(location).body(announcementService.save(announcement));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PutMapping("/save/{id}")
    public ResponseEntity update(@RequestBody AnnouncementSaveDTO announcementBody, @PathVariable("id") Long id) {
        try {
            Announcement announcement = announcementService.createAnnouncementFromSaveDTO(announcementBody, id);
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
}
