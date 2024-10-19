package com.kunuz.controller;

import com.kunuz.dto.ArticleTypeDto;
import com.kunuz.entity.ArticleTypeEntity;
import com.kunuz.service.ArticleTypeService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article_type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("/create")
    public ResponseEntity<ArticleTypeDto> create(@RequestBody ArticleTypeDto articleTypeDto) {
        return ResponseEntity.ok(articleTypeService.create(articleTypeDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleTypeDto> update(@RequestBody ArticleTypeDto articleTypeDto,
                                                 @PathVariable("id") Long id) {
        return ResponseEntity.ok(articleTypeService.update(articleTypeDto, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(articleTypeService.delete(id));
    }

    @PostMapping("/get-all")
    public ResponseEntity<PageImpl<ArticleTypeDto>> getAll(@RequestParam(name = "page", defaultValue = "1") int page,
                                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(articleTypeService.getAll(page - 1, size));
    }
}
