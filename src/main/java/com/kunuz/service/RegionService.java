package com.kunuz.service;


import com.kunuz.dto.RegionDto;
import com.kunuz.dto.RegionShortDto;
import com.kunuz.entity.RegionEntity;
import com.kunuz.enums.AppLanguage;
import com.kunuz.enums.Visible;
import com.kunuz.repository.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDto create(RegionDto regionDto) {
        RegionEntity region = new RegionEntity();

        mapping(regionDto, region);
        regionRepository.save(region);
        return regionDto;
    }

    public RegionDto update(Long id, RegionDto regionDto) {
        RegionEntity region = regionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Region with id " + id + " not found"));

        mapping(regionDto, region);
        regionRepository.save(region);
        return regionDto;
    }

    private void mapping(RegionDto regionDto, RegionEntity region) {
        region.setOrderNumber(regionDto.getOrderNumber());
        region.setNameUz(regionDto.getNameUz());
        region.setNameRu(regionDto.getNameRu());
        region.setNameEn(regionDto.getNameEn());
        region.setCreatedDate(LocalDateTime.now());
    }

    public String delete(Long id) {
        RegionEntity region = regionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Region with id " + id + " not found"));
        region.setVisible(Boolean.FALSE);
        regionRepository.save(region);
        return "Deleted!";
    }

    public List<RegionDto> getAll() {
        List<RegionEntity> all = regionRepository.getAll();
        List<RegionDto> dtoList = new LinkedList<>();
        for (RegionEntity regionEntity : all) {
            RegionDto regionDto = new RegionDto();
            regionDto.setId(regionEntity.getId());
            regionDto.setOrderNumber(regionEntity.getOrderNumber());
            regionDto.setNameUz(regionEntity.getNameUz());
            regionDto.setNameRu(regionEntity.getNameRu());
            regionDto.setNameEn(regionEntity.getNameEn());
            dtoList.add(regionDto);
        }
        return dtoList;
    }

    public List<RegionShortDto> getByLanguage(AppLanguage language) {
        List<RegionEntity> all = regionRepository.getAll();
        List<RegionShortDto> dtoList = new LinkedList<>();

        for (RegionEntity regionEntity : all) {
            RegionShortDto regionDto = new RegionShortDto();

            regionDto.setId(regionEntity.getId());
            regionDto.setName(switch (language) {
                case en -> regionEntity.getNameEn();
                case ru -> regionEntity.getNameRu();
                default -> regionEntity.getNameUz();
            });
            dtoList.add(regionDto);
        }
        return dtoList;
    }
}
