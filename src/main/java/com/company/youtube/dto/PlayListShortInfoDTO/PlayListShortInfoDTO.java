package com.company.youtube.dto.PlayListShortInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayListShortInfoDTO {
    private String id;
    private String name;
    private LocalDateTime createdDate = LocalDateTime.now();
    private ChannelDTO channel;
    private Integer viewCount;
    private List<VideoDTO> videoList;
}
