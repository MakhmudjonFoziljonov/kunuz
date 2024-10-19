package com.kunuz.entity;

import com.kunuz.enums.Visible;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "article_types")
public class ArticleTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_number")
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    @Enumerated(EnumType.STRING)
    private Visible visible;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
