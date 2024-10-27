package com.kunuz.entity;

import com.kunuz.enums.SmsStatus;
import com.kunuz.enums.SmsType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.angus.mail.imap.protocol.BODY;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "sms_histories")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  id;
    @Column(name = "code")
    private String code;
    @Column(name = "message")
    private String message;
    @Column(name = "phone")
    private String phone;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "attempt_count")
    private Integer attemptCount = 0;

    @Column(name = "type")
    private SmsType type;
}
