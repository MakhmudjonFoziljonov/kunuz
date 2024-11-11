package com.kunuz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "article_types")
public class ArticleTypesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @Column(name = "types_id")
    private Long typesId;
    @ManyToOne
    @JoinColumn(name = "types_id", insertable = false, updatable = false)
    private ArticleTypeEntity types;
}