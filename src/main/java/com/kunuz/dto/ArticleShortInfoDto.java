package com.kunuz.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleShortInfoDto {
    private String id;
    private String title;
    private String description;
    private AttachDto image;
    private LocalDateTime publishedDate;
}
