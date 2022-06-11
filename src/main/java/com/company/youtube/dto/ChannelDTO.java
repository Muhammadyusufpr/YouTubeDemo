package com.company.youtube.dto;

import com.company.youtube.enams.ChannelStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ChannelDTO extends BaseDTO{
    private String name;
    private String description;
    private ChannelStatus status;
    private String key;
    private AttachDTO image;
    private AttachDTO banner;

}
