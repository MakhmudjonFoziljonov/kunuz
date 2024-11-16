package com.kunuz.controller;

import com.kunuz.dto.SaveArticleDto;
import com.kunuz.service.SaveArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/save")
public class SaveArticleController {
    @Autowired
    private SaveArticleService saveArticleService;

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','MODERATOR')")
    public ResponseEntity<String> saveArticle(@RequestParam(name = "id") String id) {
        return ResponseEntity.ok(saveArticleService.saveArticle(id));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','MODERATOR')")
    public ResponseEntity<String> deleteArticleInBasket(@RequestParam(name = "id") String id) {
        return ResponseEntity.ok(saveArticleService.deleteArticleInBasket(id));
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','MODERATOR')")
    public List<SaveArticleDto> getAll() {
        return saveArticleService.getAll();
    }


}
