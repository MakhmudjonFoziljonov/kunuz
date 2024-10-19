package com.kunuz.controller;

import com.kunuz.dto.RegionDto;
import com.kunuz.dto.RegionShortDto;
import com.kunuz.enums.AppLanguage;
import com.kunuz.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/create")
    public ResponseEntity<RegionDto> create(@RequestBody RegionDto regionDto) {
        return ResponseEntity.ok(regionService.create(regionDto));
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<RegionDto> update(@PathVariable("id") Long id,
                                             @RequestBody RegionDto regionDto) {
        return ResponseEntity.ok(regionService.update(id, regionDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(regionService.delete(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<RegionDto>> getAll() {
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping("/getByLanguage")
    public ResponseEntity<List<RegionShortDto>> getByLanguage(@RequestHeader(value = "Accept-language", defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(regionService.getByLanguage(language));
    }

}
