package com.kunuz.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ArticleTypeDto {
    private String id;
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;


    public ArticleTypeDto() {

    }
}
