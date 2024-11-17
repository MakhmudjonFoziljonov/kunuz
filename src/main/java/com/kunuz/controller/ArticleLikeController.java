package com.kunuz.controller;

import com.kunuz.dto.ArticleLikeDto;
import com.kunuz.enums.AppLanguage;
import com.kunuz.enums.LikeStatus;
import com.kunuz.service.ArticleLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/like")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','MODERATOR')")
    public ResponseEntity<ArticleLikeDto> like(@RequestParam("articleId") String articleId) {
        return ResponseEntity.ok(articleLikeService.like(articleId));
    }

    @PostMapping("/dislike")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','MODERATOR')")
    public ResponseEntity<ArticleLikeDto> dislike(@RequestParam("articleId") String articleId) {
        return ResponseEntity.ok(articleLikeService.dislike(articleId));
    }

    @PostMapping("/remove")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','MODERATOR')")
    public String remove(@RequestParam("articleId") String articleId,
                         @RequestHeader(value = "Accept-language", defaultValue = "UZ") AppLanguage language) {
        return articleLikeService.remove(articleId, language);
    }
}
