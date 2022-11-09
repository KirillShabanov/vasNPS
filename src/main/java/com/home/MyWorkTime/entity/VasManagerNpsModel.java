package com.home.MyWorkTime.entity;

import lombok.*;
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
    private String position_call_nps;
    @Column
    private String position_call_nps_copy;
    @Column
    private String position_department;
    @Column
    private String position_mail_nps_week;
    @Column
    private String position_mail_nps_week_copy;
    @Column
    private String brand;
    @Column
    private String organisation;
    @Column
    private String position_mail_nps_month;
    @Column
    private String position_mail_nps_month_copy;

}
