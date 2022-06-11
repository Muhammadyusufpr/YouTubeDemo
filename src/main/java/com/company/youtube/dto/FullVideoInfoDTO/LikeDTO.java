package com.company.youtube.dto.FullVideoInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private String id;
    private Integer likeCount;
    private Integer disLikeCount;
    private Boolean isUserLiked;
    private Boolean isUserDisliked;
}
