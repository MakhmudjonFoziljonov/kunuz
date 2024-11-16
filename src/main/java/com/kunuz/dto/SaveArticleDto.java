package com.kunuz.dto;

import lombok.Data;

@Data
public class SaveArticleDto {
    private Long id;
    private ArticleShortInfoRecord article;
}
