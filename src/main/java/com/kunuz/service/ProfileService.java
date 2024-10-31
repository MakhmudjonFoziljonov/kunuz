package com.kunuz.service;

import com.kunuz.dto.profile.ProfileCreationDTO;
import com.kunuz.dto.profile.ProfileDetailDto;
import com.kunuz.dto.profile.ProfileDto;
import com.kunuz.entity.ProfileEntity;
import com.kunuz.enums.ProfileStatus;
import com.kunuz.exps.AppBadRequestException;
import com.kunuz.repository.ProfileRepository;
import com.kunuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;


    public ProfileDto createProfile(ProfileCreationDTO request) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(request.getEmail());
        if (optional.isPresent()) {
            throw new AppBadRequestException("Email already in use");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(request.getName());
        entity.setSurname(request.getSurname());
        entity.setEmail(request.getEmail());
        entity.setPassword(MD5Util.md5(request.getPassword()));
        entity.setRole(request.getRole());
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        return mapToDTO(entity);
    }

    public ProfileDto mapToDTO(ProfileEntity entity) {
        ProfileDto dto = new ProfileDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());

        return dto;
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
            profileDto.setRole(entity.getRole());
//            profileDto.setStatus(Status.NOT_PUBLISHED);
            list.add(profileDto);
        }
        return new PageImpl<>(list, pageRequest, totalElements);
    }

    public boolean updateDetail(@Valid ProfileDetailDto requestDTO, String username) {
        //check with username
        ProfileEntity profile = getByUsername(username);
        profile.setName(requestDTO.getName());
        profile.setSurname(requestDTO.getSurname());
        profileRepository.save(profile);

        return true;
    }


    public ProfileEntity getByUsername(String username) {
        return profileRepository.findByEmailAndVisibleTrue(username).orElseThrow(() -> new AppBadRequestException("User not found"));
    }

//
//    public FilterDto<ProfileEntity> filter(ProfileDto profileDto) {
//        return customProfileRepository.filter(profileDto);
//
//    }


  /*  public String delete(Long id) {
        profileRepository.detele(id);
        return "Deleted";
    }*/

}





