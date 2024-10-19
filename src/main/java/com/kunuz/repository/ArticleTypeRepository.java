package com.kunuz.repository;

import com.kunuz.entity.ArticleEntity;
import com.kunuz.entity.ArticleTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Long> {

    @Query(value = "select * from article_types where visible is true", nativeQuery = true)
    List<ArticleTypeEntity> getByLang();

    @Query(value = "select * from article_types", nativeQuery = true)
    Page<ArticleTypeEntity> getAll(Pageable pageable);
}
