package com.kunuz.repository;

import com.kunuz.dto.ArticleDto;
import com.kunuz.entity.ArticleEntity;
import com.kunuz.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
    Iterable<ArticleEntity> findAll();

    @Modifying
    @Transactional
    @Query(value = "update artilces set visible = false where id=?1", nativeQuery = true)
    void deleteById(String id);


    @Modifying
    @Transactional
    @Query(value = "update artilces set status = 'NOT_PUBLISHED' where id=?1", nativeQuery = true)
    void changeStatusById(String id);

//    @Query(value = "SELECT a FROM articles a WHERE a.type = :type ORDER BY a.createdDate DESC", nativeQuery = true)
//    List<ArticleEntity> findTop5ByTypeOrderByCreatedDateDesc(@Param("type") String type);

}
