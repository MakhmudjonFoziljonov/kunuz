package com.kunuz.service;

import com.kunuz.dto.ArticleTypeDto;
import com.kunuz.dto.ArticleTypeShortDto;
import com.kunuz.entity.ArticleTypeEntity;
import com.kunuz.enums.AppLanguage;
import com.kunuz.enums.Visible;
import com.kunuz.repository.ArticleTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;


    public ArticleTypeDto create(ArticleTypeDto articleTypeDto) {
        ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();

        mapping(articleTypeDto, articleTypeEntity);
        articleTypeRepository.save(articleTypeEntity);
        return articleTypeDto;
    }

    private void mapping(ArticleTypeDto articleTypeDto, ArticleTypeEntity articleTypeEntity) {
        articleTypeEntity.setOrderNumber(articleTypeDto.getOrderNumber());
        articleTypeEntity.setNameEn(articleTypeDto.getNameEn());
        articleTypeEntity.setNameRu(articleTypeDto.getNameRu());
        articleTypeEntity.setNameUz(articleTypeDto.getNameUz());
        articleTypeEntity.setCreatedDate(LocalDateTime.now());
        articleTypeEntity.setVisible(Boolean.TRUE);
    }


    public ArticleTypeDto update(ArticleTypeDto articleTypeDto, Long id) {
        ArticleTypeEntity entity = articleTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ArticleTypeEntity with id " + id + " not found"));

        mapping(articleTypeDto, entity);
        articleTypeRepository.save(entity);
        return articleTypeDto;
    }


    public String delete(Long id) {
        ArticleTypeEntity entity = articleTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ArticleTypeEntity with id " + id + " not found"));
        entity.setVisible(Boolean.FALSE);
        articleTypeRepository.save(entity);
        return "Deleted!";
    }

    public PageImpl getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ArticleTypeEntity> all = articleTypeRepository.getAll(pageRequest);
        Long totalElements = all.getTotalElements();

        if (all.isEmpty()) {
            System.out.println("No articles found.");
            return new PageImpl<>(List.of(), pageRequest, 0);
        }

        List<ArticleTypeDto> articleTypeDtoList = new LinkedList<>();

        for (ArticleTypeEntity entity : all) {
            ArticleTypeDto articleTypeDto = new ArticleTypeDto();

            articleTypeDto.setOrderNumber(entity.getOrderNumber());
            articleTypeDto.setNameUz(entity.getNameUz());
            articleTypeDto.setNameRu(entity.getNameRu());
            articleTypeDto.setNameEn(entity.getNameEn());
            articleTypeDtoList.add(articleTypeDto);
        }

        return new PageImpl<>(articleTypeDtoList, pageRequest, totalElements);
    }

    public List<ArticleTypeShortDto> getByLang(AppLanguage language) {
        List<ArticleTypeEntity> entities = articleTypeRepository.getByLang();
        List<ArticleTypeShortDto> shortDtoList = new LinkedList<>();

        for (ArticleTypeEntity entity : entities) {
            ArticleTypeShortDto articleTypeShortDto = new ArticleTypeShortDto();

            articleTypeShortDto.setId(entity.getId());
            articleTypeShortDto.setName(switch (language) {
                case ru -> entity.getNameRu();
                case en -> entity.getNameEn();
                default -> entity.getNameUz();
            });
            shortDtoList.add(articleTypeShortDto);
        }
        return shortDtoList;

    }
}
