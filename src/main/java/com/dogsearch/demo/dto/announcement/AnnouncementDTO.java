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
    private String category;
    private Long subCategoryId;
    private List<byte[]> images;

    public AnnouncementDTO(Long id, String title, String text, String personName, String category, Long subCategoryId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.personName = personName;
        this.category = category;
        this.subCategoryId = subCategoryId;
    }
}
