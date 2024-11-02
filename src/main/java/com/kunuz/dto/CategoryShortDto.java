package com.kunuz.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.DeleteMapping;

@Data
public class CategoryShortDto {
    private Long id;
    private String name;
}
