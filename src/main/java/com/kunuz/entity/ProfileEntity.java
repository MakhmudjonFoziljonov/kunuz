package com.kunuz.entity;


import com.kunuz.enums.ProfileEnums;
import com.kunuz.enums.ProfileStatus;
import com.kunuz.enums.Visible;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "profiles")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "visible")
    private Boolean visible;

    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    @Enumerated(EnumType.STRING)
    private ProfileEnums role;

    @ManyToOne
    @JoinColumn(name = "photo_id")
    private AttachEntity photo_id;

}
