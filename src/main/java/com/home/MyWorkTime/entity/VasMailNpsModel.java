package com.home.MyWorkTime.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "vas_nps")
@NoArgsConstructor
@AllArgsConstructor
public class VasMailNpsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String client_surname;
    @Column
    private String client_name;
    @Column
    private String phone_1;
    @Column
    private String phone_2;
    @Column
    private String vin;
    @Column
    private String reg_num;
    @Column
    private String brand;
    @Column
    private String model;
    @Column
    private Long year_release;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_order;
    @Column
    private Long num_order;
    @Column
    private String master_name;
    @Column
    private String department;
    @Column
    private Integer nps;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date call_date;
    @Column
    private String call_status;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outgoing_call_date;
    @Column
    private String admin_name;
    @Column
    private String admin_comment;
}
