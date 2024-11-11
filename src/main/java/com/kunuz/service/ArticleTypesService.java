package com.kunuz.service;


import com.kunuz.dto.ArticleTypeDto;
import com.kunuz.entity.ArticleTypesEntity;
import com.kunuz.repository.custom.ArticleTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypesService {

    @Autowired
    private ArticleTypesRepository articleTypesRepository;

    public void create(String articleId, List<ArticleTypeDto> articleTypeDtoList) {
        if (articleTypeDtoList == null) {
            return;
        }

        for (ArticleTypeDto typeDto : articleTypeDtoList) {
            ArticleTypesEntity entity = new ArticleTypesEntity();
            entity.setArticleId(articleId);
            entity.setTypesId(typeDto.getId());
            articleTypesRepository.save(entity);
        }
    }

    public void merge(String articleId, List<ArticleTypeDto> articleTypeDtoList){

    }
}
