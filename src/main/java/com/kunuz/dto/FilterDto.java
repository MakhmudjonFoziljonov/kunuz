package com.kunuz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterDto<T> {
    private List<T> content;
    private Long total;

    public FilterDto(List<T> content) {
        this.content = content;
    }
}
