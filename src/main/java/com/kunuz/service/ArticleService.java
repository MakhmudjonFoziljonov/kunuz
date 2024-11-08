package com.kunuz.service;


import com.kunuz.dto.ArticleDto;
import com.kunuz.entity.ArticleEntity;
import com.kunuz.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public ArticleDto create(ArticleDto articleDto) {

        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(articleDto.getTitle());
        entity.setOrderNumber(entity.getOrderNumber());
        entity.setDescription(articleDto.getDescription());
        entity.setContent(articleDto.getContent());
        entity.setImageID(articleDto.getImageId());
        entity.setRegionId(articleDto.getRegionId());
        entity.setCategoryID(articleDto.getCategoryId());
//        entity.setArticleTypeList(articleDto.getArticleTypeList());
        articleRepository.save(entity);

        return articleDto;
    }

    public List<ArticleDto> getAllArticles() {
        Iterable<ArticleEntity> all = articleRepository.findAll();
        List<ArticleDto> dtos = new LinkedList<>();

        for (ArticleEntity entity : all) {
            ArticleDto articleDto = new ArticleDto();
            articleDto.setId(entity.getId());
            articleDto.setDescription(entity.getDescription());
            articleDto.setTitle(entity.getTitle());
            articleDto.setImageId(entity.getImageID());
            articleDto.setCategoryId(entity.getCategoryID());
            articleDto.setRegionId(entity.getRegionId());
            articleDto.setContent(entity.getContent());
            dtos.add(articleDto);
        }
        return dtos;
    }

    public String delete(String id) {
        articleRepository.deleteById(id);
        return "Deleted";
    }

    public String changeStatus(String id) {
        articleRepository.changeStatusById(id);
        return "Status Changed!";
    }

//    public ResponseEntity<ArticleDto> getLatest(String type) {
//        articleRepository.findTop5ByTypeOrderByCreatedDateDesc(type);
//        return null;
//    }
}
