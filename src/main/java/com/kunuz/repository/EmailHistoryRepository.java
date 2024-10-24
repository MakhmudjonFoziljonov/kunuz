package com.kunuz.repository;

import com.kunuz.dto.EmailHistoryDto;
import com.kunuz.entity.EmailHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Long>,
        PagingAndSortingRepository<EmailHistoryEntity, Long> {
    List<EmailHistoryEntity> findByEmail(String email);

    List<EmailHistoryEntity> findByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

    Page<EmailHistoryEntity> findAll(Pageable pageable);
}
