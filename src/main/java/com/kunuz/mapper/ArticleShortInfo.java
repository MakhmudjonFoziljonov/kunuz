package com.kunuz.mapper;

import com.kunuz.dto.AttachDto;

import java.time.LocalDateTime;

public interface ArticleShortInfo {
    String getId();

    String getTitle();

    String getDescription();

    String getStatus();

    AttachDto getImageId();

    LocalDateTime getPublishDate();

}
