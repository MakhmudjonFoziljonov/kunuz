package com.kunuz.controller;

import com.kunuz.dto.ArticleTypeDto;
import com.kunuz.dto.FilterDto;
import com.kunuz.dto.ProfileDto;
import com.kunuz.entity.ProfileEntity;
import com.kunuz.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileDto profileDto) {
        return ResponseEntity.ok(profileService.create(profileDto));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfileDto> update(@RequestBody ProfileDto profileDto,
                                             @PathVariable("id") Long id) {
        return ResponseEntity.ok(profileService.update(profileDto, id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<ProfileDto>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(profileService.getAll(page - 1, size));

    }

/*    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(profileService.delete(id));
    }*/

    @PostMapping("/filter")
    public ResponseEntity<FilterDto<ProfileEntity>> filter(@RequestBody ProfileDto profileDto) {
        return ResponseEntity.ok(profileService.filter(profileDto));
    }
}
