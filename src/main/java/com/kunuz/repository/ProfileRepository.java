package com.kunuz.repository;

import com.kunuz.dto.ProfileDto;
import com.kunuz.entity.ProfileEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Long>,
        PagingAndSortingRepository<ProfileEntity, Long> {

    @Query(value = "select * from profiles", nativeQuery = true)
    Page<ProfileEntity> getAll(Pageable pageable);

   /* @Modifying
    @Transactional
    @Query(value = "update profile set ")
    void detele(Long id);*/
}
