package com.kunuz.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailHistoryDto {
    private Long id;
    private String message;
    private String email;
}
