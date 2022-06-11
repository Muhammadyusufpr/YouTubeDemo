package com.company.youtube.dto;

import com.company.youtube.enams.VideoStatus;
import com.company.youtube.enams.VideoType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTO extends BaseDTO {
    private String previewAttachId;
    private String title;
    private String categoryId;
    private String attachId;
    private VideoStatus status;
    private VideoType type;
    private Integer likeCount;
    private Integer disLikeCount;
    private Integer shareCount;
    private String description;
    private String channelId;

}
