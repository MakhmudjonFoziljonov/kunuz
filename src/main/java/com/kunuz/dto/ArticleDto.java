package com.kunuz.dto;

import com.kunuz.entity.*;
import jdk.jfr.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleDto {
    private String id;
    private String title;
    private String description;
    private String content;

    private AttachEntity imageId;
    private RegionEntity regionId;
    private CategoryEntity categoryId;

    private List<ArticleTypeEntity> articleTypeList;
}
