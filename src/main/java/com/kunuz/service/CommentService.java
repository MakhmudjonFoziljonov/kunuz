package com.kunuz.service;

import com.kunuz.dto.CommentDto;
import com.kunuz.entity.ArticleEntity;
import com.kunuz.entity.CommentEntity;
import com.kunuz.enums.AppLanguage;
import com.kunuz.enums.Visible;
import com.kunuz.repository.CommentRepository;
import com.kunuz.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ResourceBundleService resourceBundleService;

    public CommentDto create(CommentDto dto) {

        CommentEntity entity = new CommentEntity();


        entity.setContent(dto.getContent());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setArticleId(dto.getArticleId());
        entity.setUpdateDate(LocalDateTime.now());
        entity.setReplyId(dto.getReplyId());
        entity.setVisible(Visible.ACTIVE);

        entity.setProfileId(getProfileId());
        commentRepository.save(entity);

        return dto;
    }


    public String update(CommentDto dto, String id, AppLanguage language) {
        var entity = commentRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException(resourceBundleService.getMessage("No.articles.found", language)));

        entity.setArticleId(dto.getArticleId());
        entity.setReplyId(dto.getReplyId());
        commentRepository.save(entity);

        return "Your comment was updated, bro!";
    }

    private static Long getProfileId() {
        return SpringSecurityUtil.getCurrentUserId();
    }
}
