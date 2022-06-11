package com.company.youtube.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@Table(name = TagEntity.TABLE_NAME)
@Entity
public class TagEntity extends BaseEntityWithUIID {

    static final String TABLE_NAME = "tag";
    private String name;
}
