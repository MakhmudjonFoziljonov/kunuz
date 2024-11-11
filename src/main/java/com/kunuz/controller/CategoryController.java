package com.kunuz.controller;

import com.kunuz.dto.CategoryDto;
import com.kunuz.dto.CategoryShortDto;
import com.kunuz.enums.AppLanguage;
import com.kunuz.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @PostMapping("/create")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto dto,
                                              @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.update(dto, id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.delete(id));

    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("get-by-lang")
    public ResponseEntity<List<CategoryShortDto>> getByLang(
            @RequestHeader(value = "Accept-language", defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.getByLang(language));
    }


}
