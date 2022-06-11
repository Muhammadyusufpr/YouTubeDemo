package com.company.youtube.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListVideoDTO {
    private String id;
    private String videoId;
    private String playListId;
    private Integer orderNum;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private VideoDTOForPlaylistVideoDTO videoDTO;
}
