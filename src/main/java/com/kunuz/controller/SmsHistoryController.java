package com.kunuz.controller;


import com.kunuz.dto.SmsHistoryDto;
import com.kunuz.service.SmsHistoryService;
import com.kunuz.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms")
public class SmsHistoryController {
    @Autowired
    private SmsHistoryService service;

/*    @PostMapping("/create")
    public ResponseEntity<SmsHistoryDto> create(@RequestBody SmsHistoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody SmsHistoryDto dto,
                                         @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.update(dto, id));
    }*/

    @GetMapping("get-by-phone/{phone}")
    public ResponseEntity<SmsHistoryDto> getByPhone(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(service.getByPhone(phone));
    }

    @GetMapping("get-All")
    private ResponseEntity<Page<SmsHistoryDto>> getAll(@RequestParam(name = "page", defaultValue = "1") int page,
                                                       @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAll(page - 1, size));
    }

}
