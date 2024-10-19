package com.kunuz.repository;

import com.kunuz.entity.ArticleEntity;
import com.kunuz.entity.ArticleTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Long> {

    @Query(value = "select * from article_types where id=:id", nativeQuery = true)
    ArticleTypeEntity getById(@Param("id") Long id);

    @Query(value = "select * from article_types", nativeQuery = true)
    Page<ArticleEntity> getAll(Pageable pageable);
}
