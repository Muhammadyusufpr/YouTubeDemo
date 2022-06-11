package com.company.youtube.dto;

import com.company.youtube.enams.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private ProfileRole role;
    private AttachDTO image;

    private String jwt;

    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;


}
