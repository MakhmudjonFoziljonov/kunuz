package com.kunuz.dto.profile;

import com.kunuz.dto.AttachDto;
import com.kunuz.enums.ProfileRole;
import com.kunuz.enums.ProfileStatus;
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
    private ProfileStatus status;
    private ProfileRole role;
    private String jwtToken;
    private AttachDto photo;
}
