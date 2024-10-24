package com.kunuz.service;

import com.kunuz.dto.EmailHistoryDto;
import com.kunuz.entity.EmailHistoryEntity;
import com.kunuz.repository.EmailHistoryRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class EmailSendingService {
    @Value("${spring.mail.username}")
    private String fromAccount;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailHistoryRepository repository;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;


    public String sendMimeMessage(String to, String subject, String text) {

        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
        emailHistoryEntity.setEmail(to);
        emailHistoryEntity.setMessage(text);
        emailHistoryEntity.setCreatedDate(LocalDateTime.now());
        repository.save(emailHistoryEntity);

        return "Mail was send";
    }

    public List<EmailHistoryEntity> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate) {
        LocalDateTime fromDate = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(endDate, LocalTime.MAX);
        return emailHistoryRepository.findByCreatedDateBetween(fromDate, toDate);
    }

    public PageImpl<EmailHistoryDto> pagination(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<EmailHistoryEntity> all = emailHistoryRepository.findAll(pageRequest);
        long totalElements = all.getTotalElements();

        List<EmailHistoryDto> dtos = new LinkedList<>();

        for (EmailHistoryEntity list : all) {

            EmailHistoryDto emailHistoryDto = new EmailHistoryDto();
            emailHistoryDto.setMessage(list.getMessage());
            emailHistoryDto.setEmail(list.getEmail());
            dtos.add(emailHistoryDto);
        }
        return new PageImpl<>(dtos, pageRequest, totalElements);
    }
}