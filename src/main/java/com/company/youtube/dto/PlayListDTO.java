package com.company.youtube.dto;

import com.company.youtube.enams.PlayListStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PlayListDTO {
    private String id;
    private String channelId;
    private String name;
    private String description;
    private PlayListStatus status;
    private Integer orderNum;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
