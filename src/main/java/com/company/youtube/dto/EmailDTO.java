package com.company.youtube.dto;

import com.company.youtube.enams.EmailType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailDTO {
    private String id;
    private String toEmail;
    private EmailType type;
    private LocalDateTime sendDate = LocalDateTime.now();
}
