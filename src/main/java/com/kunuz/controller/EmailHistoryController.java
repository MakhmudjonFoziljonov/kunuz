package com.kunuz.controller;


import com.kunuz.dto.EmailHistoryDto;
import com.kunuz.entity.EmailHistoryEntity;
import com.kunuz.entity.RegionEntity;
import com.kunuz.repository.EmailHistoryRepository;
import com.kunuz.service.EmailSendingService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/email-history")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private EmailSendingService emailSendingService;

    @GetMapping("/by-email")
    public ResponseEntity<List<EmailHistoryEntity>> getEmailHistoryByEmail(@RequestParam String email) {
        return ResponseEntity.ok(emailHistoryRepository.findByEmail(email));
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<EmailHistoryEntity>> getEmailBydDate(@RequestParam LocalDate startDate,
                                                                    @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(emailSendingService.findByCreatedDateBetween(startDate, endDate));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<EmailHistoryDto>> pagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(emailSendingService.pagination(page - 1, size));
    }

}