package com.kunuz.controller;


import com.kunuz.service.ArticleTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article-types")
public class ArticleTypesController {
    @Autowired
    private ArticleTypesService articleTypesService;

   /* @PostMapping("/create")
    public ResponseEntity<ArticleTypesDto> create(@RequestBody ArticleTypeDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(articleTypesService.create(dto));
    }*/

}
