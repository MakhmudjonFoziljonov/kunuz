package com.kunuz.repository;

import com.kunuz.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Long>, PagingAndSortingRepository<ProfileEntity, Long> {

    @Query(value = "select * from profiles", nativeQuery = true)
    Page<ProfileEntity> getAll(Pageable pageable);

    Optional<ProfileEntity> findById(Integer id);

    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);

    Optional<ProfileEntity> findByPhone(String phone);



   /* @Modifying
    @Transactional
    @Query(value = "update profile set ")
    void detele(Long id);*/
}
