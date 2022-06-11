package com.company.youtube.dto;

import com.company.youtube.dto.VideoShortInfoDTO.ChannelDTO;
import com.company.youtube.enams.NotificationType;
import com.company.youtube.enams.SubscriptionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionDTO {
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer profileId;
    private String channelId;
    private SubscriptionStatus status;
    private NotificationType type;

    private ChannelDTO channelDTO;
}
