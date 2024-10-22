package com.kunuz.service;


import com.kunuz.dto.ArticleDto;
import com.kunuz.entity.ArticleEntity;
import com.kunuz.entity.ArticleTypeEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleService {
    public ArticleDto create(ArticleDto articleDto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(articleDto.getTitle());
        entity.setOrderNumber(entity.getOrderNumber());
        entity.setDescription(articleDto.getDescription());
        entity.setContent(articleDto.getContent());
        entity.setImageID(articleDto.getImageId());
        entity.setRegionId(articleDto.getRegionId());
        entity.setCategoryID(articleDto.getCategoryId());
        List<ArticleTypeEntity> list = new LinkedList<>(articleDto.getArticleTypeList());
        return articleDto;
    }
}
