package com.company.youtube.entity;

import com.company.youtube.enams.ReportType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class ReportEntity extends BaseEntityWithUIID {
    @Column(columnDefinition = "text")
    private String content;

    @Column
    private Integer toId;
    @Column
    @Enumerated(EnumType.STRING)
    private ReportType type;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;


}
