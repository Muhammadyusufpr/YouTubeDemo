package com.company.youtube.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
public class BaseEntityWithUIID {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false,nullable = false)
    protected String id;

    @Column(name = "created_date")
    protected LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "updated_date")
    protected LocalDateTime updatedDate;
    @Column(name = "deleted_date")
    protected LocalDateTime deletedDate;
    @Column(name = "published_date")
    protected LocalDateTime publishedDate;
}
