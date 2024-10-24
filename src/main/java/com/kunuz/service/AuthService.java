package com.kunuz.service;

import com.kunuz.dto.RegistrationDTO;
import com.kunuz.entity.ProfileEntity;
import com.kunuz.enums.ProfileStatus;
import com.kunuz.enums.Visible;
import com.kunuz.repository.ProfileRepository;
import com.kunuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailSendingService emailSendingService;


    public String registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.md5(dto.getPassword()));
        entity.setSurname(dto.getSurname());
        entity.setStatus(ProfileStatus.IN_REGISTRATION);
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);

        StringBuilder sb = new StringBuilder();
        sb.append("<h1 style=\"text-align: center\"> Complete Registration</h1>");
        sb.append("<br>");
        sb.append("<p>Click the link below to complete registration</p>\n");
        sb.append("<p><a style=\"padding: 5px; background-color: indianred; color: white\" href=\"http://localhost:8080/auth/registration/confirm/")
                .append(entity.getId()).append("\" target=\"_blank\">Click Th</a></p>\n");
        emailSendingService.sendMimeMessage(dto.getEmail(), "Complite Registration", sb.toString());

        int beginMinute = LocalDateTime.now().getMinute();
        int minute = entity.getCreatedDate().getMinute();
        int i1 = beginMinute - minute;
        if (i1 > 4) {
        }

        int i = 0;
        while (i < 4) {
            emailSendingService.sendMimeMessage(dto.getEmail(), "Complite Registration", sb.toString());
            i++;
        }

        return "Email was sent";
    }

    public String registrationConfirm(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isPresent()) {
            return "Not Completed";
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.IN_REGISTRATION)) {
            return "Not Completed";
        }
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        return "Completed";
    }


}
