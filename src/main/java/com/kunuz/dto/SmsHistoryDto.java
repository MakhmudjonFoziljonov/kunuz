package com.kunuz.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SmsHistoryDto {
    private String  id;
    private String code;
    private String phone;
    private String message;
    private LocalDateTime createdDate;
}
