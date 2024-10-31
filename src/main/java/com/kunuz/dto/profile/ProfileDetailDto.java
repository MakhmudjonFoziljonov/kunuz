package com.kunuz.dto.profile;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProfileDetailDto {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
}
