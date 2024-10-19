package com.kunuz.dto;

import com.kunuz.enums.ProfileEnums;
import com.kunuz.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private Status status;
    private ProfileEnums role;
}
