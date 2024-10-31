package com.kunuz.controller;


import com.kunuz.dto.ArticleDto;
import com.kunuz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("create")
    public ResponseEntity<ArticleDto> create(@RequestBody ArticleDto articleDto){
        return ResponseEntity.ok(articleService.create(articleDto));
    }
}
