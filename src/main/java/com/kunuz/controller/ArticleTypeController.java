package com.kunuz.controller;

import com.kunuz.dto.ArticleTypeDto;
import com.kunuz.dto.ArticleTypeShortDto;
import com.kunuz.entity.ArticleTypeEntity;
import com.kunuz.enums.AppLanguage;
import com.kunuz.mapper.ArticleTypeInfoMapper;
import com.kunuz.service.ArticleTypeService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article_type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("/create")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleTypeDto> create(@RequestBody ArticleTypeDto articleTypeDto) {
        return ResponseEntity.ok(articleTypeService.create(articleTypeDto));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleTypeDto> update(@RequestBody ArticleTypeDto articleTypeDto, @PathVariable("id") Long id) {
        return ResponseEntity.ok(articleTypeService.update(articleTypeDto, id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(articleTypeService.changeVisible(id));
    }

    @PostMapping("/get-all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Page<ArticleTypeDto>> getAll(@RequestParam(name = "page", defaultValue = "1") int page,
                                                       @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(articleTypeService.getAll(page - 1, size));
    }

    @GetMapping("/get-by-lang")
    public ResponseEntity<List<ArticleTypeInfoMapper>> getByLang(@RequestHeader(value = "Accept-language", defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(articleTypeService.getByLang(language));
    }
}
