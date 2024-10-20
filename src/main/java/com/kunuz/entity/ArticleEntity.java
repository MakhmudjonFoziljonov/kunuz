package com.kunuz.entity;

import com.kunuz.enums.Status;
import com.kunuz.enums.Visible;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;


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
    @Column(name = "image_id")
    private Long imageID;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity regionId;

/*    @ManyToOne
    @JoinColumn(name = "categoty_id")
    private Category categoryID;
    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private Moderator moderatorId;
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher_id;*/

    @Column(name = "status")
    private Status status;
    @Column(name = "created_date")
    private LocalDateTime created_date;
    @Column(name = "published_date")
    private LocalDateTime published_date;
    @Column(name = "visible")
    private Visible visible;
    @Column(name = "view_count")
    private Integer view_count;

    private String nameUz;
    private String nameRu;
    private String nameEn;


}
