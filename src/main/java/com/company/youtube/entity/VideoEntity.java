package com.company.youtube.entity;

import com.company.youtube.enams.VideoStatus;
import com.company.youtube.enams.VideoType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "video")
public class VideoEntity extends BaseEntityWithUIID {


    @Column(name = "preview_attach_id")
    private String previewAttachId;

    @Column
    private String title;

    @Column(name = "category_id")
    private String categoryId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;


    @Column(name = "attach_id")
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column
    @Enumerated(EnumType.STRING)
    private VideoStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private VideoType type;


    @OneToMany(fetch = FetchType.LAZY)
    List<VideoLikeEntity> likeEntity;

    @Column(name = "share_count")
    private Integer shareCount;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "description")
    private String description;

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


    @Column(name = "video_tags")
    @OneToMany(fetch = FetchType.LAZY)
    private List<VideoTagEntity> videoTagEntities;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment", insertable = false, updatable = false)
    private List<CommentEntity> commentEntities;

}
