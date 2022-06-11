package com.company.youtube.dto.VideoShortInfoDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoShortInfoDTO {
    private String id;
    private String title;
    private LocalDateTime publishedDate;
    private Integer viewCount;
    private Double duration; // TODO


    private PreviewAttachDTO previewAttach;
    private ChannelDTO channelDTO;


}
