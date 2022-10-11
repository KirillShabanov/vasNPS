package com.home.MyWorkTime.entity;


import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "vas_nps")
@NoArgsConstructor
@AllArgsConstructor
public class VasNpsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String owner_full_name;
    @Column
    private String client_full_name;
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
    @DateTimeFormat(pattern = "dd.MM.yyyy")
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

    public Date getDate_order() {
        return date_order;
    }

    public String getDate_orders() {
        return date_order.toString().formatted(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VasNpsModel that = (VasNpsModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
