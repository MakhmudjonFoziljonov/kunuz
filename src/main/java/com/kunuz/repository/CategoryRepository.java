package com.kunuz.repository;

import com.kunuz.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "update categories set visible = false where id=?1", nativeQuery = true)
    void deleteById(Long id);
}
