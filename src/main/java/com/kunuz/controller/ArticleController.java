package com.kunuz.controller;


import com.kunuz.dto.ArticleDto;
import com.kunuz.dto.ArticleFilterDto;
import com.kunuz.dto.ArticleShortInfoDto;
import com.kunuz.enums.AppLanguage;
import com.kunuz.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ArticleDto> create(@RequestBody ArticleDto articleDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.create(articleDto, request));
    }

/*    @GetMapping("/get-all")
    public List<ArticleDto> getAllArticles() {
        return articleService.getAllArticles();
    }*/

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String delete(@PathVariable("id") String id) {
        return articleService.delete(id);
    }

    @PutMapping("/change-status/{id}")
    public String changeStatus(@PathVariable("id") String id) {
        return articleService.changeStatus(id);
    }

    @GetMapping("/get-last-five")
    public ResponseEntity<List<ArticleDto>> getLastFive(@RequestParam(name = "type") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getLastFive(id));
    }

    @GetMapping("/get-last-three")
    public ResponseEntity<List<ArticleDto>> getLastThree(@RequestParam(name = "type") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getLastThree(id));
    }

    @GetMapping("/get-last-four")
    public ResponseEntity<List<ArticleDto>> getLastFour(@RequestParam(name = "type") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getLastFour(id));
    }

    @GetMapping("/get-last-five-by-region-key")
    public ResponseEntity<List<ArticleDto>> getLastFiveRegionKey(@RequestParam(name = "typeId") Long typeId, @RequestParam(name = "regionId") Long regionId) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getLastFiveRegionKey(typeId, regionId));
    }

    @GetMapping("/last8")
    public List<ArticleShortInfoDto> getLast8(@RequestParam(required = false) List<String> excludeIds) {
        return articleService.getLast8(excludeIds != null ? excludeIds : List.of());

    }


    @GetMapping("/view-count/{articleId}")
    public String trackViewCount(@PathVariable String articleId, HttpServletRequest request,
                                 @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return articleService.trackView(articleId, request, language);
    }

    @GetMapping("/share-count/{articleId}")
    public String trackShareCount(@PathVariable String articleId,
                                  @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return articleService.trackShareCount(articleId, language);
    }

    @GetMapping("/filter")
    public ResponseEntity<PageImpl<ArticleShortInfoDto>> filter(@RequestBody ArticleFilterDto articleFilterDto, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.filter(articleFilterDto, page - 1, size));
    }

    @GetMapping("/get-article-by-category")
    public ResponseEntity<Page<ArticleShortInfoDto>> getByCategory(@RequestParam Long categoryId,
                                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getByCategory(categoryId, page - 1, size));
    }


//    @GetMapping("get-latest")
//    public ResponseEntity<ArticleDto> getLatest(@RequestParam) {
//        return articleService.getLatest();
//    }

}