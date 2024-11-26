package com.kunuz.controller;

import com.kunuz.dto.*;
import com.kunuz.dto.profile.ProfileDto;
import com.kunuz.enums.AppLanguage;
import com.kunuz.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("auth/")
@Tag(name = "Authorization controller")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationDTO dto,
                                               @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARNING");
        log.error("ERROR");

        return ResponseEntity.ok(authService.registration(dto, lang));
    }

    @GetMapping("/registration/confirm/{id}")
    public ResponseEntity<String> registration(@PathVariable Integer id) {
        return ResponseEntity.ok(authService.registrationConfirm(id));
    }

    @PostMapping("sms-registration")
    public ResponseEntity<String> smsRegistration(RegistrationDTO dto) {
        return ResponseEntity.ok(authService.smsRegistration(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDto> login(@RequestBody @Valid AuthDto dto,
                                            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {

        return ResponseEntity.ok(authService.login(dto, lang));
    }


}
