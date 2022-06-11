package com.company.youtube.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class VideoTagDTO {
    private String id;
    private String tagId;
    private String videoId;
    private LocalDateTime createdDate = LocalDateTime.now();
}
