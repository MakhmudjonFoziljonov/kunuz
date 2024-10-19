package com.kunuz.repository;


import com.kunuz.dto.RegionDto;
import com.kunuz.entity.RegionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Long> {

    @Query(value = "select * from regions where visible = true", nativeQuery = true)
    List<RegionEntity> getAll();
}
