package com.company.youtube.dto;

import com.company.youtube.dto.VideoShortInfoDTO.ChannelDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTOForPlaylistVideoDTO {
    private String id;
    private AttachDTO previewAttach;
    private String title;
    private ChannelDTO channel;
    private LocalDateTime createdDate;
}
