package com.company.youtube.entity;

import com.company.youtube.enams.ChannelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "channel")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelEntity extends BaseEntityWithUIID {

    @Column
    private String name;
    @Column
    private String description;

    @Column(name = "attach_id")
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column
    @Enumerated(EnumType.STRING)
    ChannelStatus status;

    @Column(name = "banner_id")
    private String bannerId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id", insertable = false, updatable = false)
    private AttachEntity attachBanner;

    @Column(name = "profile_id")
    private Integer profileId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column
    private String key;


    @OneToMany(fetch = FetchType.LAZY)
    private List<VideoEntity> video;
}
