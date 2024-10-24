package com.kunuz.dto;

import com.kunuz.enums.ProfileEnums;
import com.kunuz.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private ProfileStatus status;
    private ProfileEnums role;
}
