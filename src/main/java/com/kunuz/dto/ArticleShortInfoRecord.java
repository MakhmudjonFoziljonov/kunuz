package com.kunuz.dto;

import com.kunuz.entity.AttachEntity;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ArticleShortInfoRecord(
        String id,
        String description,
        String title,
        LocalDateTime publishedDate,
        AttachRecord image
) {
}
