package com.kunuz.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PostDto implements Serializable {
    private Long id;
    private String title;
    private String content;
    private List<AttachDto> attachDtoList;
}
