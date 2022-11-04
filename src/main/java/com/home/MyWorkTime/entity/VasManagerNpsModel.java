package com.home.MyWorkTime.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vas_manager")
@NoArgsConstructor
@AllArgsConstructor
public class VasManagerNpsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String manager_name;
    @Column
    private String manager_email;
    @Column
    private String position;
    @Column
    private String brand;
    @Column
    private String organisation;
}
