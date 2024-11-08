package com.kunuz.entity;

import com.kunuz.enums.Status;
import com.kunuz.enums.Visible;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "articles")
public class ArticleEntity {
    @Id
    @UuidGenerator
    private String id;
    @Column(name = "title", length = 20)
    private String title;
    @Column(name = "order_number")
    private Integer orderNumber;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "content")
    private String content;
    @Column(name = "shared_count")
    private Integer sharedCount;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private AttachEntity imageID;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity regionId;

    @ManyToOne
    @JoinColumn(name = "categoty_id")
    private CategoryEntity categoryID;

    @Column(name = "status")
    private Status status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "visible")
    private Visible visible;

    @Column(name = "view_count")
    private Integer view_count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_type_id")
    private ArticleTypeEntity articleTypeEntity;

    private String nameUz;
    private String nameRu;
    private String nameEn;


}
