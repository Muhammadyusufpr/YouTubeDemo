package com.company.youtube.dto.FullVideoInfoDTO;

import com.company.youtube.dto.TagDTO;
import com.company.youtube.dto.VideoShortInfoDTO.ChannelDTO;
import com.company.youtube.dto.VideoShortInfoDTO.PreviewAttachDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FullVideoInfoDTO {
    private String id;
    private String title;
    private String description;
    private PreviewAttachDTO previewAttach;
    private AttachDTO attach;
    private CategoryDTO category;
    private List<TagDTO> tagList;
    private LocalDateTime publishedDate;
    private ChannelDTO channel;
    private Integer viewCount;
    private Integer shareCount;
    private LikeDTO likeDTO;
    private Double duration;
}
