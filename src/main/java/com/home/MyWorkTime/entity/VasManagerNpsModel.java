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

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "vas_manager")
public class VasManagerNpsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "manager_name")
    private String manager_name;
    @Column(name = "manager_email")
    private String manager_email;
    @Column(name = "position_call_nps")
    private String position_call_nps;
    @Column(name = "position_call_nps_copy")
    private String position_call_nps_copy;
    @Column(name = "position_department")
    private String position_department;
    @Column(name = "position_mail_nps_week")
    private String position_mail_nps_week;
    @Column(name = "position_mail_nps_week_copy")
    private String position_mail_nps_week_copy;
    @Column(name = "brand")
    private String brand;
    @Column(name = "organisation")
    private String organisation;
    @Column(name = "position_mail_nps_month")
    private String position_mail_nps_month;
    @Column(name = "position_mail_nps_month_copy")
    private String position_mail_nps_month_copy;
    @Column(name = "position_report_satisfaction_kia")
    private String position_report_satisfaction_kia;
    @Column(name = "position_report_satisfaction_kia_copy")
    private String position_report_satisfaction_kia_copy;
    @Column(name = "position_report_haval")
    private String position_report_haval;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VasManagerNpsModel that = (VasManagerNpsModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
