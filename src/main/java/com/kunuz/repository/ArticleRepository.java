package com.kunuz.repository;

import com.kunuz.dto.ArticleDto;
import com.kunuz.dto.ArticleFilterDto;
import com.kunuz.entity.ArticleEntity;
import com.kunuz.enums.Status;
import com.kunuz.mapper.ArticleShortInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, String>,
        PagingAndSortingRepository<ArticleEntity, String> {
    Iterable<ArticleEntity> findAll();

    @Modifying
    @Transactional
    @Query(value = "update articles set visible = 'DELETED' where id=?1", nativeQuery = true)
    void deleteById(String id);


    @Modifying
    @Transactional
    @Query(value = "update articles set status = 'NOT_PUBLISHED' where id=?1", nativeQuery = true)
    void changeStatusById(String id);

//    @Query(value = "SELECT a FROM articles a WHERE a.type = :type ORDER BY a.createdDate DESC", nativeQuery = true)
//    List<ArticleEntity> findTop5ByTypeOrderByCreatedDateDesc(@Param("type") String type);

    @Query("SELECT a FROM ArticleEntity a WHERE a.articleTypeEntity.id = :typeId ORDER BY a.createdDate DESC limit 5")
    List<ArticleEntity> findTop5ByTypeIdOrderByCreatedDateDesc(@Param("typeId") Long typeId);


    @Query("SELECT a FROM ArticleEntity a WHERE a.articleTypeEntity.id = :typeId ORDER BY a.createdDate DESC limit 3")
    List<ArticleEntity> findTop3ByTypeIdOrderByCreatedDateDesc(@Param("typeId") Long typeId);


    @Query("SELECT a FROM ArticleEntity a WHERE a.articleTypeEntity.id <> :typeId ORDER BY a.createdDate DESC limit 4")
    List<ArticleEntity> findTop4ByNotTypeIdOrderByCreatedDateDesc(@Param("typeId") Long typeId);

    @Query("SELECT a FROM ArticleEntity a WHERE a.articleTypeEntity.id = :typeId and a.region.id = :regionId ORDER BY a.createdDate DESC limit 5")
    List<ArticleEntity> findByTop5ByTypeIdAndRegionIdOrderByCreatedDateDesc(@Param("typeId") Long typeId, @Param("regionId") Long regionId);

    @Query(" select a.id as id,  a.title as title, a.description as description, a.image as imageId, a.publishedDate as publishedDate " + "  From ArticleEntity a  where  a.id not in ?2 and a.status =?1 order by a.createdDate desc")
    List<ArticleShortInfo> getLastIdNotIn(Status status, List<String> excludeIdList, Pageable pageable);

    @Query(value = "select * from articles where id = :articleId and ip_address = :ipAddress", nativeQuery = true)
    Optional<ArticleEntity> finByArticleIdAndIpAddress(@Param("articleId") String articleId, @Param("ipAddress") String ipAddress);

    @Query(value = "select * from articles where id = ?1", nativeQuery = true)
    Optional<ArticleEntity> findByArticleId(String articleId);

    @Query(value = "select ar.* from articles ar where ar.category_id= ?1 ORDER BY ar.id", nativeQuery = true)
    List<ArticleEntity> findByCategoryId(Long categoryId, Pageable pageable);


    @Query("SELECT COUNT(ar) FROM ArticleEntity ar WHERE ar.category.id = ?1")
    long countByCategoryId(Long categoryId);
}
