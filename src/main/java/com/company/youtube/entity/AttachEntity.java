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
@Entity
@Table(name = "attach")
public class AttachEntity extends BaseEntityWithUIID {

    @Column
    private String path;
    @Column
    private String extension;
    @Column(name = "origen_name")
    private String origenName;
    @Column
    private Long size;



}
