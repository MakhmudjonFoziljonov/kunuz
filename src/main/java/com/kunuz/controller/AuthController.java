package com.kunuz.controller;

import com.kunuz.dto.AuthDto;
import com.kunuz.dto.ProfileDto;
import com.kunuz.dto.RegistrationDTO;
import com.kunuz.dto.SmsHistoryDto;
import com.kunuz.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth/")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
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
    public ResponseEntity<ProfileDto> login(@RequestBody  @Valid AuthDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }


}
