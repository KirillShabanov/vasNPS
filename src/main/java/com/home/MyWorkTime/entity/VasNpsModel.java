package com.home.MyWorkTime.entity;


import javax.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String client_surname;
    @Column
    private String client_name;
    @Column
    private String phone_1;
    @Column
    private String phone_2;
    @Column
    private String vehicle_identification_number;
    @Column
    private String reg_num;
    @Column
    private String brand;
    @Column
    private String model;
    @Column
    private String year_release;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_order;
    @Column
    private String num_order;
    @Column
    private Long mileage;
    @Column
    private String organisation;
    @Column
    private String department;
    @Column
    private String category;
    @Column
    private String master_name;
    @Column
    private Integer nps;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date mail_date;
    @Column
    private String call_status;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outgoing_call_date;
    @Column
    private String admin_name;
    @Column
    private String admin_comment;
    @Column
    private String calendar_client;


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
