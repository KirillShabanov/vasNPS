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
