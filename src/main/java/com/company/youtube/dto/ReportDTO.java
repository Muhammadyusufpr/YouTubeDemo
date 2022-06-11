package com.company.youtube.dto;

import com.company.youtube.enams.ReportType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDTO {
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String content;
    private Integer toId;
    private Integer profileId;
    private ReportType type;

    private ProfileDTO profileDTO;
}
