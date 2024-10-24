package com.kunuz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;
}
