package com.home.MyWorkTime.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "fix_brand_model")
public class FixBrandModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fix_key")
    private String fix_key;
    @Column(name = "fix_value")
    private String fix_value;

}
