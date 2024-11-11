package com.kunuz.controller;


import com.kunuz.dto.ArticleDto;
import com.kunuz.dto.ArticleShortInfoDto;
import com.kunuz.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<ArticleDto> create(@RequestBody ArticleDto articleDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.create(articleDto, request));
    }

/*    @GetMapping("/get-all")
    public List<ArticleDto> getAllArticles() {
        return articleService.getAllArticles();
    }*/

    @DeleteMapping("/delete/{id}")
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
    public ResponseEntity<List<ArticleDto>> getLastFiveRegionKey(@RequestParam(name = "typeId") Long typeId,
                                                                 @RequestParam(name = "regionId") Long regionId) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getLastFiveRegionKey(typeId, regionId));
    }

    @GetMapping("/last8")
    public List<ArticleShortInfoDto> getLast8(@RequestParam(required = false) List<String> excludeIds) {
        return articleService.getLast8(excludeIds != null ? excludeIds : List.of());

    }


    @GetMapping("/view-count/{articleId}")
    public String trackViewCount(@PathVariable String articleId, HttpServletRequest request) {
        return articleService.trackView(articleId, request);
    }

    @GetMapping("/share-count/{articleId}")
    public String trackShareCount(@PathVariable String articleId) {
        return articleService.trackShareCount(articleId);
    }


//    @GetMapping("get-latest")
//    public ResponseEntity<ArticleDto> getLatest(@RequestParam) {
//        return articleService.getLatest();
//    }

}