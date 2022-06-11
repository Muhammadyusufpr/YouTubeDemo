package com.company.youtube.entity;

import com.company.youtube.enams.EmailType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "email_history")
@Getter
@Setter
public class EmailEntity extends BaseEntityWithUIID {

    @Column
    private String toEmail;
    @Column
    @Enumerated(EnumType.STRING)
    private EmailType type;
}
