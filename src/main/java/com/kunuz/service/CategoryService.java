package com.kunuz.service;

import com.kunuz.dto.CategoryDto;
import com.kunuz.entity.CategoryEntity;
import com.kunuz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDto create(CategoryDto dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        categoryRepository.save(entity);
        return dto;
    }

    public CategoryDto update(CategoryDto dto, Long id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        categoryRepository.save(entity);
        return dto;
    }

    public String delete(Long id) {
        categoryRepository.deleteById(id);
        return "Deleted";
    }

    public List<CategoryDto> getAll() {
        Iterable<CategoryEntity> all = categoryRepository.findAll();

        List<CategoryDto> dtoList = new LinkedList<>();
        for (CategoryEntity entity : all) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setOrderNumber(entity.getOrderNumber());
            categoryDto.setNameUz(entity.getNameUz());
            categoryDto.setNameRu(entity.getNameRu());
            categoryDto.setNameEn(entity.getNameEn());
            dtoList.add(categoryDto);
        }
        return dtoList;
    }
}
