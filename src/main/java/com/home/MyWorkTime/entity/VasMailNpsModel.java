/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  
 * 
 *  Date of remmark: 27/07/2023
 */
package com.home.MyWorkTime.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Column(name = "client_surname")
    private String client_surname;
    @Column(name = "client_name")
    private String client_name;
    @Column(name = "phone_1")
    private String phone_1;
    @Column(name = "phone_2")
    private String phone_2;
    @Column(name = "vehicle_identification_number")
    private String vehicle_identification_number;
    @Column(name = "reg_num")
    private String reg_num;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "year_release")
    private String year_release;
    @Column(name = "date_order")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_order;
    @Column(name = "num_order")
    private String num_order;
    @Column(name = "organisation")
    private String organisation;
    @Column(name = "department")
    private String department;
    @Column(name = "category")
    private String category;
    @Column(name = "master_name")
    private String master_name;
    @Column(name = "nps")
    private Integer nps;
    @Column(name = "mail_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date mail_date;
    @Column(name = "call_status")
    private String call_status;
    @Column(name = "outgoing_call_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outgoing_call_date;
    @Column(name = "admin_name")
    private String admin_name;
    @Column(name = "admin_comment")
    private String admin_comment;
    @Column(name = "mileage")
    private Long mileage;
}
