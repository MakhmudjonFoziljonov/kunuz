package com.kunuz.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "article_view_count")
public class ArticleViewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "ip_address")
    private String ipAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ip_address", updatable = false, insertable = false)
    private ArticleEntity article;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
