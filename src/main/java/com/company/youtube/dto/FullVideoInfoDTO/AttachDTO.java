package com.company.youtube.dto.FullVideoInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttachDTO {
    private String id;
    private String url;
    private Double duration;

}
