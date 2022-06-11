package com.company.youtube.dto;

import com.company.youtube.enams.LikeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoLikeDTO extends BaseDTO {
    private Integer profileId;
    private String videoId;
    private LikeType likeType;
}
