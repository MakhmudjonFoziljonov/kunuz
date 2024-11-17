package com.kunuz.controller;

import com.kunuz.dto.AttachDto;
import com.kunuz.enums.AppLanguage;
import com.kunuz.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<AttachDto> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(attachService.upload(file));
    }

    @GetMapping("/open/{fileName}")
    public ResponseEntity<Resource> open(@PathVariable String fileName,
                                         @RequestHeader(value = "Accept-language", defaultValue = "UZ")
                                         AppLanguage language) {
        return attachService.open(fileName,language);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName,
                                             @RequestHeader(value = "Accept-language", defaultValue = "UZ")
                                             AppLanguage language) {
        return attachService.download(fileName,language);
    }


}
