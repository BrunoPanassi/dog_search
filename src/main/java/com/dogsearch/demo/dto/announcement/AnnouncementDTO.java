package com.dogsearch.demo.dto.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class AnnouncementDTO {
    private Long id;
    private String title;
    private String text;
    private String personName;
}
