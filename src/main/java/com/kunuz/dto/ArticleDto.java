package com.kunuz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kunuz.entity.*;
import com.kunuz.service.ArticleService;
import jdk.jfr.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDto {
    private String id;
    private String title;
    private String description;
    private String content;
    private String ipAddress;

    private AttachEntity imageId;
    private RegionEntity regionId;
    private CategoryEntity categoryId;
    private ArticleTypeEntity articleTypeId;

    private List<ArticleTypeDto> articleTypeDtoList;

  /*  private String  imageId;
    private Long regionId;
    private Long categoryId;
    private List<Long> articleTypeDtoList;*/

}
