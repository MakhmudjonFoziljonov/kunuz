package com.kunuz.repository;

import com.kunuz.entity.PostAttachEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostAttachRepository extends CrudRepository<PostAttachEntity, Long> {
    @Query(value = "select attach_id from post_attachs where post_id =?1", nativeQuery = true)
    List<String> findAllByPostId(Long postId);

    @Modifying
    @Transactional
    @Query("delete from PostAttachEntity where postId =?1")
    void deleteByPostId(Integer postId);

    @Modifying
    @Transactional
    @Query("delete from PostAttachEntity where postId =?1 and attachId = ?2")
    void deleteByPostIdAndAttachId(Long postId, String attachId);

}
