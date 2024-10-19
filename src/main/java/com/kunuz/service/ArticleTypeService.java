package com.kunuz.service;

import com.kunuz.dto.ArticleTypeDto;
import com.kunuz.entity.ArticleEntity;
import com.kunuz.entity.ArticleTypeEntity;
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

//    public ArticleTypeDto toDto(ArticleTypeDto articleTypeDto) {
//        ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
//
//        articleTypeEntity.setOrderNumber(articleTypeDto.getOrderNumber());
//        articleTypeEntity.setNameEn(articleTypeDto.getNameEn());
//        articleTypeEntity.setNameRu(articleTypeDto.getNameRu());
//        articleTypeEntity.setNameUz(articleTypeDto.getNameUz());
//        articleTypeEntity.setCreatedDate(LocalDateTime.now());
//        articleTypeEntity.setVisible(Visible.ACTIVE);
//        articleTypeRepository.save(articleTypeEntity);
//
//        return articleTypeDto;
//    }

    public ArticleTypeDto create(ArticleTypeDto articleTypeDto) {
        ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();

        articleTypeEntity.setOrderNumber(articleTypeDto.getOrderNumber());
        articleTypeEntity.setNameEn(articleTypeDto.getNameEn());
        articleTypeEntity.setNameRu(articleTypeDto.getNameRu());
        articleTypeEntity.setNameUz(articleTypeDto.getNameUz());
        articleTypeEntity.setCreatedDate(LocalDateTime.now());
        articleTypeEntity.setVisible(Visible.ACTIVE);
        articleTypeRepository.save(articleTypeEntity);
        return articleTypeDto;
    }


    public ArticleTypeDto update(ArticleTypeDto articleTypeDto, Long id) {
        ArticleTypeEntity entity = articleTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ArticleTypeEntity with id " + id + " not found"));

        articleTypeDto.setId(entity.getId());
        entity.setOrderNumber(articleTypeDto.getOrderNumber());
        entity.setNameEn(articleTypeDto.getNameEn());
        entity.setNameRu(articleTypeDto.getNameRu());
        entity.setNameUz(articleTypeDto.getNameUz());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(Visible.ACTIVE);
        articleTypeRepository.save(entity);
        return articleTypeDto;
    }


    public String delete(Long id) {
        ArticleTypeEntity entity = articleTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ArticleTypeEntity with id " + id + " not found"));
        entity.setVisible(Visible.DELETED);
        articleTypeRepository.save(entity);
        return "Deleted!";
    }

    public PageImpl getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ArticleEntity> all = articleTypeRepository.getAll(pageRequest);
        Long totalElements = all.getTotalElements();

        if (all.isEmpty()) {
            System.out.println("No articles found.");
            return new PageImpl<>(List.of(), pageRequest, 0);
        }


        List<ArticleTypeDto> articleTypeDtoList = new LinkedList<>();

        for (ArticleTypeDto dtoList : articleTypeDtoList) {
            Long id = dtoList.getId();
        }

        return new PageImpl<>(dtoList, pageRequest, totalElements);
    }
}
