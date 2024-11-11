package com.kunuz.repository.custom;

import com.kunuz.entity.ArticleTypesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTypesRepository extends CrudRepository<ArticleTypesEntity, Long> {
}
