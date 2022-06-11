package com.company.youtube.entity;

import com.company.youtube.enams.ProfileRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntityWithUIID {


    @Column
    private String name;


}
