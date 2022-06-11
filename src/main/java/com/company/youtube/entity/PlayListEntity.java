package com.company.youtube.entity;

import com.company.youtube.enams.PlayListStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name = "play_list")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListEntity extends BaseEntityWithUIID {


    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Integer viewCount;

    @Column
    @Enumerated(EnumType.STRING)
    private PlayListStatus status;

    @Column
    private Integer orderNum;


}
