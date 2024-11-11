package com.kunuz.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ArticleTypeDto {
    private Long id;
    private Integer orderNumber;

    private String nameUz;
    private String nameRu;
    private String nameEn;

    public ArticleTypeDto(){

    }
}
