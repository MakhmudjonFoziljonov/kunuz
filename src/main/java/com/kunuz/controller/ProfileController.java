package com.kunuz.controller;

import com.kunuz.dto.ArticleTypeDto;
import com.kunuz.dto.FilterDto;
import com.kunuz.dto.JwtDto;
import com.kunuz.dto.ProfileDto;
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

    @PostMapping("/create")
    public ResponseEntity<ProfileDto> createProfile(@RequestBody @Valid ProfileCreationDTO profileDto,
                                                    @RequestHeader("Authorization") String token) {
        System.out.println(token);
        JwtDto dto = JwtUtil.decode(token.substring(7));

        return ResponseEntity.ok(profileService.createProfile(profileDto));
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<ProfileDto> update(@RequestBody ProfileCreationDTO profileDto,
//                                             @PathVariable("id") Long id) {
//        return ResponseEntity.ok(profileService.update(profileDto, id));
//    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<ProfileDto>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(profileService.getAll(page - 1, size));

    }

/*    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(profileService.delete(id));
    }*/

//    @PostMapping("/filter")
//    public ResponseEntity<FilterDto<ProfileEntity>> filter(@RequestBody ProfileDto profileDto) {
//        return ResponseEntity.ok(profileService.filter(profileDto));
//    }

}
