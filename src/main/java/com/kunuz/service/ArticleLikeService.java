package com.kunuz.service;


import com.kunuz.dto.ArticleLikeDto;
import com.kunuz.entity.ArticleLikeEntity;
import com.kunuz.enums.LikeStatus;
import com.kunuz.repository.ArticleLikeRepository;
import com.kunuz.util.SpringSecurityUtil;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public ArticleLikeDto like(String articleId) {

        var entity = articleLikeRepository.findByProfileIdAndArticleId(getProfileId(), articleId);

        if (entity == null) {
            entity = new ArticleLikeEntity();
            entity.setArticleId(articleId);
            entity.setProfileId(getProfileId());
            entity.setStatus(LikeStatus.LIKE);
            entity.setLikeCount(0L);
            entity.setDislikeCount(0L);
            entity.setCreatedDate(LocalDateTime.now());
        }
        entity.setStatus(LikeStatus.LIKE);
        articleLikeRepository.save(entity);
        updateLikeDislikeCount(articleId, entity.getStatus());

        return mapper(entity);
    }


    public ArticleLikeDto dislike(String articleId) {

        var entity = articleLikeRepository.findByProfileIdAndArticleId(getProfileId(), articleId);

        if (entity == null) {
            entity = new ArticleLikeEntity();
            entity.setArticleId(articleId);
            entity.setProfileId(getProfileId());
            entity.setStatus(LikeStatus.DISLIKE);
            entity.setCreatedDate(LocalDateTime.now());
        }
        entity.setStatus(LikeStatus.DISLIKE);
        articleLikeRepository.save(entity);
        updateLikeDislikeCount(articleId, entity.getStatus());

        return mapper(entity);
    }

    public String remove(String articleId) {
        var entity = articleLikeRepository.findByProfileIdAndArticleId(getProfileId(), articleId);
        if (entity == null) {
            throw new RuntimeException("No article found with the given articleId and ipAddress");
        }
        articleLikeRepository.deleteByProfileIdAndArticleId(getProfileId(), articleId);
        return "Like or dislike removed";
    }

    private void updateLikeDislikeCount(String articleId, LikeStatus status) {
        var entity = articleLikeRepository.findByArticleId(articleId);

        if (status == LikeStatus.LIKE) {
            entity.setLikeCount(entity.getLikeCount() + 1);
        } else if (status == LikeStatus.DISLIKE) {
            entity.setDislikeCount(entity.getDislikeCount() + 1);
        }
        articleLikeRepository.save(entity);
    }

    private static Long getProfileId() {
        return SpringSecurityUtil.getCurrentUserId();
    }


    private ArticleLikeDto mapper(ArticleLikeEntity entity) {
        return new ArticleLikeDto(entity.getProfileId(),
                entity.getArticleId(),
                entity.getStatus(),
                entity.getCreatedDate(),
                entity.getLikeCount(),
                entity.getDislikeCount());
    }

}
