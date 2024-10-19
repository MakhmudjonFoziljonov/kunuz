package com.kunuz.entity;

import com.kunuz.enums.Visible;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "regions")
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private Integer orderNumber;
    @Column(name = "visible")
    private Visible visible;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    private String nameUz;
    private String nameRu;
    private String nameEn;

}
