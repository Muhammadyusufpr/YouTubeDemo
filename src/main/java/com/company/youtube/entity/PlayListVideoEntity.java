package com.company.youtube.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "playList_video")
public class PlayListVideoEntity extends BaseEntityWithUIID{

    @Column(name = "playList_id")
    private String playListId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playList_id", insertable = false, updatable = false)
    private PlayListEntity playList;

    @Column(name = "video_id")
    private String videoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", insertable = false, updatable = false)
    private VideoEntity video;

    @Column
    private Integer orderNum;


}
