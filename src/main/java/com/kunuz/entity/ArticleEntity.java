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

    @Column(name = "title", nullable = false, columnDefinition = "text")
    private String title;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "shared_count")
    private Integer sharedCount = 0;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private AttachEntity image;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity region;

    @ManyToOne
    @JoinColumn(name = "categoty_id")
    private CategoryEntity category;

    @Enumerated(EnumType.STRING)
    private Status status;


    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Enumerated(EnumType.STRING)
    private Visible visible;


    @Column(name = "view_count")
    private Integer viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_type_id")
    private ArticleTypeEntity articleTypeEntity;

    @Column(name = "publisher_id")
    private Long publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", updatable = false, insertable = false)
    private ProfileEntity publisher;

    @Column(name = "moderator_id")
    private Long moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", updatable = false, insertable = false)
    private ProfileEntity moderator;


    private String nameUz;
    private String nameRu;
    private String nameEn;


}
