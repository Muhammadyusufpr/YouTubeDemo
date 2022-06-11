package com.company.youtube.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {
    private String id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
