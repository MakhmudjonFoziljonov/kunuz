package com.kunuz.service;

import com.kunuz.dto.ProfileDto;
import com.kunuz.entity.ProfileEntity;
import com.kunuz.enums.ProfileEnums;
import com.kunuz.enums.Status;
import com.kunuz.repository.ProfileRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDto create(ProfileDto profileDto) {
        ProfileEntity profile = new ProfileEntity();

        profile.setName(profileDto.getName());
        profile.setPhone(profileDto.getPhone());
        profile.setEmail(profileDto.getEmail());
        profile.setSurname(profileDto.getSurname());
        profile.setPhone(profileDto.getPassword());
        profile.setRole(ProfileEnums.USER);
        profile.setStatus(Status.PUBLISHED);
        profileRepository.save(profile);
        return profileDto;
    }
}
