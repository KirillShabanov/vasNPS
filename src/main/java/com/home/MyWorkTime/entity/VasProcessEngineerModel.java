package com.home.MyWorkTime.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vas_process_engineer")
@NoArgsConstructor
@AllArgsConstructor
public class VasProcessEngineerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String manager_name;
    @Column
    private String department;
}
