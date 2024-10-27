package com.kunuz.repository;


import com.kunuz.entity.SmsHistoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Long> {

    SmsHistoryEntity findByPhone(String phone);

    Page<SmsHistoryEntity> findAll(Pageable pageable);

    @Query(value = "select count(s.*) from sms_histories s where s.phone=?1 s.createdDate between ?2 and ?3", nativeQuery = true)
    Long getSmsCount(String phone, LocalDateTime localDateTime, LocalDateTime now);

    Optional<SmsHistoryEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);

    @Modifying
    @Transactional
    @Query("update SmsHistoryEntity set attemptCount = attemptCount + 1 where id = ?1")
    void increaseAttemptCount(String id);

//    Optional<SmsHistoryEntity> findByPhoneAndOrderByCreatedDateDesc(String phone);
}
