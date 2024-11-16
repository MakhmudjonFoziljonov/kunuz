package com.kunuz.dto;

import com.kunuz.entity.ArticleEntity;
import com.kunuz.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private String content;
    private String  articleId;
    private String   replyId;

}
