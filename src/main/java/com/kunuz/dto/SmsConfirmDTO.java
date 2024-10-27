package com.kunuz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsConfirmDTO {
    private String phone;
    private String code;
}
