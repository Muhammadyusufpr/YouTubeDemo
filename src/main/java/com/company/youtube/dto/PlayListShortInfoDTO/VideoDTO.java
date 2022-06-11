package com.company.youtube.dto.PlayListShortInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoDTO {
    private String id;
    private String name;
    private Double duration;
}
