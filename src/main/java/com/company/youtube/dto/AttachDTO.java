package com.company.youtube.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private String id;
    @NotBlank(message = "url required")
    private String url;
    private String origenName;
    private String path;
    private LocalDateTime cratedDate;

    public AttachDTO() {
    }

    public AttachDTO(String url) {
        this.url = url;
    }
}
