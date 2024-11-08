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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/add-profile")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProfileDto> addProfile(@RequestBody @Valid ProfileCreationDTO profileDto) {
        return ResponseEntity.ok(profileService.createProfile(profileDto));
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Page<ProfileDto>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(profileService.getAll(page - 1, size));
    }

    @PutMapping("/detail")
    public ResponseEntity<Boolean> updateDetail(@RequestBody @Valid ProfileDetailDto requestDTO) {
        return ResponseEntity.ok().body(profileService.updateDetail(requestDTO));
    }

}
