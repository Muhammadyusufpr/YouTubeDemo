package com.company.youtube.entity;

import com.company.youtube.enams.ProfileRole;
import com.company.youtube.enams.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private String password;

    @Column
    private Boolean visible = true;

    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    @Column
    @Enumerated(EnumType.STRING)
    private ProfileRole role;

    @Column(name = "attach_id")
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;



}
