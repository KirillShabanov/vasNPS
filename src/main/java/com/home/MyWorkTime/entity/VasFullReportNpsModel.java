package com.home.MyWorkTime.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@ToString
@Entity
@Table(name = "vas_full_report_nps")
public class VasFullReportNpsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_identification_number")
    private String vin;
    @Column(name = "outgoing_call_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outgoingCallDate;
    @Column(name = "id_client")
    private String idClient;
    @Column(name = "bq010")
    private String bq010;
    @Column(name = "bq020")
    private String bq020;
    @Column(name = "bq030")
    private String bq030;
    @Column(name = "bq030_comment")
    private String bq030Comment;
    @Column(name = "bq040")
    private String bq040;
    @Column(name = "bq050")
    private String bq050;
    @Column(name = "bq050_comment")
    private String bq050Comment;
    @Column(name = "bq060")
    private String bq060;
    @Column(name = "bq070")
    private String bq070;
    @Column(name = "bq080")
    private String bq080;
    @Column(name = "bq080_comment")
    private String bq080Comment;
    @Column(name = "sq010")
    private String sq010;
    @Column(name = "sq020")
    private String sq020;
    @Column(name = "sq030")
    private String sq030;
    @Column(name = "sq040")
    private String sq040;
    @Column(name = "sq050")
    private String sq050;
    @Column(name = "sq060")
    private String sq060;
    @Column(name = "sq070")
    private String sq070;
    @Column(name = "sq080")
    private String sq080;
    @Column(name = "sq090")
    private String sq090;
    @Column(name = "sq110")
    private String sq110;
    @Column(name = "sq120")
    private String sq120;
    @Column(name = "sq130")
    private String sq130;
    @Column(name = "sq140")
    private String sq140;
    @Column(name = "dq010")
    private String dq010;
    @Column(name = "dq020")
    private String dq020;
    @Column(name = "dq030")
    private String dq030;
    @Column(name = "dq040")
    private String dq040;
    @Column(name = "num_order")
    private String numOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VasFullReportNpsModel that = (VasFullReportNpsModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
