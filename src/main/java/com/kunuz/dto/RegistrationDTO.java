package com.kunuz.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    @NonNull
    private String name;
    private String surname;
    private String email;
    private String password;
}
