package com.company.youtube.dto.PlayListInfoDTO;

import com.company.youtube.enams.PlayListStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayListInfoDTO {
    private String id;
    private ChannelDTO channel;
    private String name;
    private String description;
    private PlayListStatus status;
    private Integer orderNum;
    private ProfileDTO profile;

}
