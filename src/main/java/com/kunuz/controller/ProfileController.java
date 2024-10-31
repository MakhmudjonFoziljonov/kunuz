package com.kunuz.controller;

import com.kunuz.dto.JwtDto;
import com.kunuz.dto.profile.ProfileCreationDTO;
import com.kunuz.dto.profile.ProfileDetailDto;
import com.kunuz.dto.profile.ProfileDto;
import com.kunuz.service.ProfileService;
import com.kunuz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/add-profile")
    public ResponseEntity<ProfileDto> addProfile(@RequestBody @Valid ProfileCreationDTO profileDto) {
        return ResponseEntity.ok(profileService.createProfile(profileDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<ProfileDto>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(profileService.getAll(page - 1, size));
    }

    @PutMapping("/detail")
    public ResponseEntity<Boolean> updateDetail(@RequestBody @Valid ProfileDetailDto requestDTO,
                                                @RequestHeader("Authorization") String token) {
        JwtDto dto = JwtUtil.decode(token.substring(7));
        return ResponseEntity.ok().body(profileService.updateDetail(requestDTO, dto.getUsername()));
    }

}
