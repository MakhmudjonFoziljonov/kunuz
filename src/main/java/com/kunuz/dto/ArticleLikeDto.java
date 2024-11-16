package com.kunuz.dto;

import com.kunuz.enums.LikeStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleLikeDto {
    private Long id;
    private Long profileId;
    private String articleId;
    private LikeStatus status;
    private LocalDateTime dateTime;
    private Long likeCount;
    private Long dislikeCount;

    public ArticleLikeDto(Long profileId,
                          String articleId,
                          LikeStatus status,
                          LocalDateTime dateTime,
                          Long likeCount,
                          Long dislikeCount) {
        this.profileId = profileId;
        this.articleId = articleId;
        this.status = status;
        this.dateTime = dateTime;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }
}
