package com.dogsearch.demo.dto.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AnnouncementDTO {
    private Long id;
    private String title;
    private String text;
    private String personName;
    private String phoneNumber;
    private String category;
    private Long subCategoryId;
    private List<byte[]> images;

    public AnnouncementDTO(Long id, String title, String text, String personName, String phoneNumber, String category, Long subCategoryId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.personName = personName;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.subCategoryId = subCategoryId;
    }
}
