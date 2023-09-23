package com.dogsearch.demo.dto.announcement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class AnnouncementSaveDTO {
    private String title;
    private String text;
    private Long personId;
    private Long subCategoryId;
    private MultipartFile[] images;
}
