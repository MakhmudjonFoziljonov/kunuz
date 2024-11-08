package com.kunuz.controller;


import com.kunuz.dto.ArticleDto;
import com.kunuz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleDto> create(@RequestBody ArticleDto articleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.create(articleDto));
    }

    @GetMapping("/get-all")
    public List<ArticleDto> getAllArticles() {
        return articleService.getAllArticles();
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        return articleService.delete(id);
    }

    @PutMapping("/change-status/{id}")
    public String changeStatus(@PathVariable("id") String id) {
        return articleService.changeStatus(id);
    }

//    @GetMapping("get-latest")
//    public ResponseEntity<ArticleDto> getLatest(@RequestParam) {
//        return articleService.getLatest();
//    }
}
