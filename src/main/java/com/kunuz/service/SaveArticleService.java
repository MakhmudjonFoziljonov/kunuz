package com.kunuz.service;

import com.kunuz.dto.SaveArticleDto;
import com.kunuz.entity.ArticleEntity;
import com.kunuz.entity.ArticleSaveEntity;
import com.kunuz.repository.ArticleRepository;
import com.kunuz.repository.SaveArticleRepository;
import com.kunuz.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SaveArticleService {
    private final SaveArticleRepository repository;
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    public String saveArticle(String articleId) {

        ArticleSaveEntity entity = new ArticleSaveEntity();
        entity.setArticleId(articleId);
        entity.setProfileId(SpringSecurityUtil.getCurrentUserId());
        entity.setCreatedDate(LocalDateTime.now());
        repository.save(entity);
        return "Saved";
    }

    public String deleteArticleInBasket(String id) {
        repository.deleteById(id);
        return "Deleted";
    }

    public List<SaveArticleDto> getAll() {
        List<ArticleSaveEntity> list = repository.findByUserId(SpringSecurityUtil.getCurrentUserId());

        List<SaveArticleDto> dtoList = new LinkedList<>();

        for (ArticleSaveEntity entity : list) {
            SaveArticleDto dto = new SaveArticleDto();
            dto.setId(entity.getId());

            Optional<ArticleEntity> optional = articleRepository.findById(entity.getArticleId());
            if (optional.isEmpty()) {
                continue;
            }
            dto.setArticle(articleService.toShortRecord(optional.get()));
            dtoList.add(dto);
        }
        return dtoList;
    }
}
