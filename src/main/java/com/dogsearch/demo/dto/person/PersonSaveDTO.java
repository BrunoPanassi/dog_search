package com.dogsearch.demo.dto.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class PersonSaveDTO {
    private String name;
    private String city;
    private String neighbourhood;
    private String phoneNumber;
}
