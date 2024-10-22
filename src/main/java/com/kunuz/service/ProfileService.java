package com.kunuz.service;

import com.kunuz.dto.ArticleTypeDto;
import com.kunuz.dto.FilterDto;
import com.kunuz.dto.ProfileDto;
import com.kunuz.entity.ProfileEntity;
import com.kunuz.enums.ProfileEnums;
import com.kunuz.enums.Status;
import com.kunuz.repository.ProfileRepository;
import com.kunuz.repository.custom.CustomProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CustomProfileRepository customProfileRepository;

    public ProfileDto create(ProfileDto profileDto) {
        ProfileEntity profile = new ProfileEntity();
        toDto(profileDto, profile);
        profileRepository.save(profile);
        return profileDto;
    }

    public ProfileDto update(ProfileDto profileDto, Long id) {
        ProfileEntity profile = profileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        toDto(profileDto, profile);
        profileRepository.save(profile);
        return null;
    }

    public void toDto(ProfileDto profileDto, ProfileEntity profile) {
        profile.setName(profileDto.getName());
        profile.setSurname(profileDto.getSurname());
        profile.setEmail(profileDto.getEmail());
        profile.setPhone(profileDto.getPhone());
        profile.setPassword(profileDto.getPassword());
        profile.setRole(ProfileEnums.USER);
        profile.setStatus(Status.NOT_PUBLISHED);
    }

    public PageImpl<ProfileDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProfileEntity> all = profileRepository.getAll(pageRequest);
        Long totalElements = all.getTotalElements();
        List<ProfileDto> list = new LinkedList<>();

        for (ProfileEntity entity : all) {
            ProfileDto profileDto = new ProfileDto();
            profileDto.setId(entity.getId());
            profileDto.setName(entity.getName());
            profileDto.setSurname(entity.getSurname());
            profileDto.setEmail(entity.getEmail());
            profileDto.setPhone(entity.getPhone());
            profileDto.setPassword(entity.getPassword());
            profileDto.setRole(ProfileEnums.USER);
            profileDto.setStatus(Status.NOT_PUBLISHED);
            list.add(profileDto);
        }
        return new PageImpl<>(list, pageRequest, totalElements);
    }

    public FilterDto<ProfileEntity> filter(ProfileDto profileDto) {
        return customProfileRepository.filter(profileDto);

    }

  /*  public String delete(Long id) {
        profileRepository.detele(id);
        return "Deleted";
    }*/

}





