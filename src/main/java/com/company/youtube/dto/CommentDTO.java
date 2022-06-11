package com.company.youtube.dto;

import com.company.youtube.enams.LikeType;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.entity.VideoEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;


@Getter
@Setter
public class CommentDTO {
    private String id;
    private Integer profileId;
    private String videoId;
    private String content;
    private String replyId;
    private LikeType likeType;

    @Column(name = "created_date")
    protected LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "updated_date")
    protected LocalDateTime updatedDate;
    @Column(name = "deleted_date")
    protected LocalDateTime deletedDate;
}
