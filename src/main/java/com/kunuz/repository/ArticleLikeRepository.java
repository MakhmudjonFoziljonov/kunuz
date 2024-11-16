package com.kunuz.repository;

import com.kunuz.entity.ArticleLikeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Long> {


    ArticleLikeEntity findByProfileIdAndArticleId(Long profileId, String articleId);

    @Transactional
    @Modifying
    @Query(value = "delete from article_likes al where al.profile_id = ?1 and al.article_id = ?2", nativeQuery = true)
    void deleteByProfileIdAndArticleId(Long profileId, String articleId);

    @Query(value = "select * from  article_likes where article_id=?1", nativeQuery = true)
    ArticleLikeEntity findByArticleId(String articleId);
}
