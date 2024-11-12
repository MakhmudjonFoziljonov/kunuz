package com.kunuz.service;


import com.kunuz.dto.ArticleDto;
import com.kunuz.dto.ArticleFilterDto;
import com.kunuz.dto.ArticleShortInfoDto;
import com.kunuz.entity.ArticleEntity;
import com.kunuz.enums.Status;
import com.kunuz.enums.Visible;
import com.kunuz.mapper.ArticleShortInfo;
import com.kunuz.repository.ArticleRepository;
import com.kunuz.repository.custom.CustomArticleFilter;
import com.kunuz.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleTypesService articleTypesService;
    private final AttachService attachService;
    private final CustomArticleFilter filter;

    public ArticleDto create(ArticleDto articleDto, HttpServletRequest request) {

        ArticleEntity entity = new ArticleEntity();
        articleDto.setIpAddress(getClientIp(request));

        entity.setTitle(articleDto.getTitle());
        entity.setOrderNumber(entity.getOrderNumber());
        entity.setDescription(articleDto.getDescription());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setContent(articleDto.getContent());
        entity.setImage(articleDto.getImageId());
        entity.setRegion(articleDto.getRegionId());
        entity.setCategory(articleDto.getCategoryId());
        entity.setIpAddress(articleDto.getIpAddress());
        entity.setArticleTypeEntity(articleDto.getArticleTypeId());

        entity.setVisible(Visible.ACTIVE);
        entity.setStatus(Status.NOT_PUBLISHED);
        entity.setSharedCount(0);
        entity.setViewCount(0);


        entity.setModeratorId(SpringSecurityUtil.getCurrentUserId());

        articleRepository.save(entity);

        articleTypesService.create(entity.getId(), articleDto.getArticleTypeDtoList());

        /*articleTypesService.merge(entity.getId(), articleDto.getArticleTypeDtoList());*/

        return articleDto;
    }

    public String delete(String id) {
        articleRepository.deleteById(id);
        return "Deleted";
    }

    public String changeStatus(String id) {
        articleRepository.changeStatusById(id);
        return "Status Changed!";
    }


    public List<ArticleDto> getLastFive(Long typeId) {
        List<ArticleEntity> articles = articleRepository.findTop5ByTypeIdOrderByCreatedDateDesc(typeId);
        return getArticleDtos(articles);


    }

    public List<ArticleDto> getLastThree(Long typeId) {
        List<ArticleEntity> articleEntities = articleRepository.findTop3ByTypeIdOrderByCreatedDateDesc(typeId);
        return getArticleDtos(articleEntities);
    }

    public List<ArticleDto> getLastFour(Long typeId) {
        List<ArticleEntity> articleEntities = articleRepository.findTop4ByNotTypeIdOrderByCreatedDateDesc(typeId);
        return getArticleDtos(articleEntities);
    }

    public List<ArticleDto> getLastFiveRegionKey(Long typeId, Long regionId) {
        List<ArticleEntity> articleEntities = articleRepository.findByTop5ByTypeIdAndRegionIdOrderByCreatedDateDesc(typeId, regionId);
        return getArticleDtos(articleEntities);
    }

    public List<ArticleShortInfoDto> getLast8(List<String> excludeIdList) {

        List<ArticleShortInfo> mapperList = articleRepository.getLastIdNotIn(Status.PUBLISHED, excludeIdList, PageRequest.of(0, 8));
//        return mapperList.stream().map(item -> toShortInfo(item)).toList();
        return mapperList.stream().map(this::toShortInfo).toList();
    }


    public ArticleShortInfoDto toShortInfo(ArticleShortInfo mapper) {
        ArticleShortInfoDto dto = new ArticleShortInfoDto();
        dto.setId(mapper.getId());
        dto.setTitle(mapper.getTitle());
        dto.setDescription(mapper.getDescription());
        dto.setPublishedDate(mapper.getPublishDate());
        dto.setImage(attachService.getDTO(mapper.getImageId().getId()));
        return dto;
    }

    public String trackView(String articleId, HttpServletRequest request) {
        String ipAddress = getClientIp(request);
        ArticleEntity article = articleRepository.finByArticleIdAndIpAddress(articleId, ipAddress)
                .orElseThrow(() -> new RuntimeException("No article found with the given articleId and ipAddress"));


        if (!(article.getViewCount() == 1)) {
            article.setViewCount(article.getViewCount() + 1);
            articleRepository.save(article);
        }
        return "View Count Updated";
    }

    public String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public String trackShareCount(String articleId) {
        ArticleEntity entity = articleRepository.findByArticleId(articleId)
                .orElseThrow(() -> new RuntimeException("No article found with the given articleId"));

        entity.setSharedCount(entity.getSharedCount());
        articleRepository.save(entity);
        return "";
    }


    @NotNull
    private static List<ArticleDto> getArticleDtos(List<ArticleEntity> articleEntities) {
        List<ArticleDto> dtoList = new LinkedList<>();

        for (ArticleEntity entity : articleEntities) {
            ArticleDto articleDto = new ArticleDto();
            articleDto.setId(entity.getId());
            articleDto.setTitle(entity.getTitle());
            articleDto.setDescription(entity.getDescription());
            articleDto.setContent(entity.getContent());
            articleDto.setImageId(entity.getImage());
            articleDto.setRegionId(entity.getRegion());
            articleDto.setCategoryId(entity.getCategory());
            articleDto.setArticleTypeId(entity.getArticleTypeEntity());
            dtoList.add(articleDto);
        }
        return dtoList;
    }

    public PageImpl<ArticleShortInfoDto> filter(ArticleFilterDto articleFilterDto, int page, int size) {

        LocalDateTime fromDate = LocalDateTime.of(articleFilterDto.getCreatedDateFrom(), LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(articleFilterDto.getCreatedDateTo(), LocalTime.MAX);
        PageImpl<ArticleEntity> resultFilter = filter.filter(articleFilterDto, page, size, toDate, fromDate);

        List<ArticleShortInfoDto> shortInfos = new LinkedList<>();
        for (ArticleEntity article : resultFilter) {
            shortInfos.add(toShortDTO(article));
        }
        return new PageImpl<>(shortInfos, resultFilter.getPageable(), resultFilter.getTotalElements());
    }


    public ArticleShortInfoDto toShortDTO(ArticleEntity entity) {

        ArticleShortInfoDto dto = new ArticleShortInfoDto();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());
        dto.setPublishedDate(entity.getPublishedDate());

        return dto;
    }

    public PageImpl<ArticleShortInfoDto> getByCategory(Long categoryId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ArticleEntity> entities = articleRepository.findByCategoryId(categoryId, pageRequest);

        long total = articleRepository.countByCategoryId(categoryId);
        List<ArticleShortInfoDto> dtoList = new LinkedList<>();

        for (ArticleEntity entity : entities) {
            dtoList.add(toShortDTO(entity));
        }
        return new PageImpl<>(dtoList, pageRequest, total);
    }
}
