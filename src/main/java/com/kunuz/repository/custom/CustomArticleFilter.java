package com.kunuz.repository.custom;


import com.kunuz.dto.ArticleFilterDto;
import com.kunuz.entity.ArticleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomArticleFilter {
    @Autowired
    private EntityManager manager;

    public PageImpl<ArticleEntity> filter(ArticleFilterDto articleFilterDto, int page, int size, LocalDateTime toDate, LocalDateTime fromDate) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (articleFilterDto.getId() != null) {
            stringBuilder.append(" and ar.id = :articleId");
            params.put("articleId", articleFilterDto.getId());
        }
        if (articleFilterDto.getTitle() != null) {
            stringBuilder.append(" and ar.title = :title");
            params.put("title", articleFilterDto.getTitle());
        }
        if (articleFilterDto.getCategoryId() != null) {
            stringBuilder.append(" and ar.category.id = :categoryId");
            params.put("categoryId", articleFilterDto.getCategoryId());
        }
        if (articleFilterDto.getTitle() != null) {
            stringBuilder.append(" and ar.region.id = :regionId");
            params.put("regionId", articleFilterDto.getRegionId());
        }
        if (articleFilterDto.getCreatedDateFrom() != null && articleFilterDto.getCreatedDateTo() != null) {
            stringBuilder.append(" and ar.created_date between :fromDate and :toDate");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        }


        StringBuilder selectBuilder = new StringBuilder("FROM ArticleEntity ar where visible = true");
        selectBuilder.append(stringBuilder);

        StringBuilder countBuilder = new StringBuilder("Select count(ar) FROM ArticleEntity ar where visible = true ");
        stringBuilder.append(countBuilder);

        Query selectQuery = manager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);                              // limit
        selectQuery.setFirstResult(page * size);                      //All Page

        Query countQuery = manager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<ArticleEntity> entities = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PageImpl<>(entities, PageRequest.of(page - 1, size), totalElements);
    }

}
