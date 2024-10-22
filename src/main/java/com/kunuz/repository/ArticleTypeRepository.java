package com.kunuz.repository;

import com.kunuz.entity.ArticleEntity;
import com.kunuz.entity.ArticleTypeEntity;
import com.kunuz.mapper.ArticleTypeInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Long> {

    @Query(value = "select * from article_types where visible is true", nativeQuery = true)
    List<ArticleTypeEntity> getByLang();

    @Query(value = "select * from article_types", nativeQuery = true)
    Page<ArticleTypeEntity> getAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query("update ArticleTypeEntity set visible = false  where id=?1")
    int changeVisible(Long id);

    @Query(value = "SELECT id AS id , order_number AS orderNumber, " +
            "       CASE ?1 " +
            "           WHEN 'uz' THEN name_uz " +
            "           WHEN 'en' THEN name_en " +
            "        ELSE name_ru " +
            "        END AS name " +
            "        FROM article_type " +
            "        WHERE visible IS true  " +
            "        ORDER BY  order_number;", nativeQuery = true)
    List<ArticleTypeInfoMapper> getByLang(String lang);

}
