package com.kunuz.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ArticleFilterDto {
    private Long id;
    private String title;
    private Long regionId;
    private Long categoryId;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private LocalDate publishedDateFrom;
    private LocalDate publishedDateTo;
    private Integer moderatorId;
    private Integer publisherId;

}
