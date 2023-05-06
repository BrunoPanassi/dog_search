package com.dogsearch.demo.dto.subCategory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SubCategorySaveDTO {
    private String name;
    private Long categoryId;
}
