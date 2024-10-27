package com.kunuz.service;

import com.kunuz.dto.SmsHistoryDto;
import com.kunuz.entity.SmsHistoryEntity;
import com.kunuz.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository repository;

   /* public SmsHistoryDto create(SmsHistoryDto dto) {
        SmsHistoryEntity smsHistoryEntity = new SmsHistoryEntity();
        smsHistoryEntity.setCode(dto.getCode());
        smsHistoryEntity.setPhone(dto.getPhone());
        smsHistoryEntity.setMessage(dto.getMessage());
        smsHistoryEntity.setStatus(Boolean.TRUE);
        smsHistoryEntity.setCreatedDate(LocalDateTime.now());
        repository.save(smsHistoryEntity);
        return dto;
    }

    public String update(SmsHistoryDto dto, Long id) {
        SmsHistoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sms not found!"));

        entity.setCode(dto.getCode());
        entity.setPhone(dto.getPhone());
        entity.setMessage(dto.getMessage());
        entity.setStatus(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        repository.save(entity);
        return "Updated";
    }*/

    public SmsHistoryDto getByPhone(String phone) {
        var entity = repository.findByPhone(phone);
        if (entity == null) {
            throw new IllegalArgumentException("Phone not found");
        }

        SmsHistoryDto dto = new SmsHistoryDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(LocalDateTime.now());
        dto.setPhone(entity.getPhone());
        return dto;
    }


    public Page<SmsHistoryDto> getAll(int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        Page<SmsHistoryEntity> all = repository.findAll(request);
        long i = all.getTotalElements();
        List<SmsHistoryDto> dtoList = new LinkedList<>();

        for (SmsHistoryEntity entity : all) {
            SmsHistoryDto dto = new SmsHistoryDto();
            dto.setId(String.valueOf(entity.getId()));
            dto.setCode(entity.getCode());
            dto.setMessage(entity.getMessage());
            dto.setPhone(entity.getPhone());
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, request, i);
    }

}
