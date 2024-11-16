package com.kunuz.repository;

import com.kunuz.entity.ArticleSaveEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveArticleRepository extends CrudRepository<ArticleSaveEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from saves where article_id=?1", nativeQuery = true)
    void deleteById(String articleId);

    @Query(value = "select * from saves where profile_id=?1", nativeQuery = true)
    List<ArticleSaveEntity> findByUserId(Long currentUserId);
}
