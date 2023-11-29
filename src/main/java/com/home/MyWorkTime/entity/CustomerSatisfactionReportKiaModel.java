/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  Database model, SMS alerts, KIA customer satisfaction report
 * 
 *  Date of remmark: 27/07/2023
 */
package com.home.MyWorkTime.entity;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;


@Entity
@Table(name = "customer_satisfaction_kia")
@Getter
@Setter
@ToString
public class CustomerSatisfactionReportKiaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_order")
    private String numOrder;
    @Column(name = "vin")
    private String vin;
    @Column(name = "sms_sending_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date smsSendingDate;
    @Column(name = "survey_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date surveyDate;
    @Column(name = "phone")
    private String phone;
    @Column(name = "id_sms")
    private String idSms;
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
    @Column(name = "bq060")
    private String bq060;
    @Column(name = "bq070")
    private String bq070;
    @Column(name = "bq080")
    private String bq080;
    @Column(name = "bq080_comment")
    private String bq080Comment;
    @Column(name = "oe030")
    private String oe030;
    @Column(name = "oe040")
    private String oe040;
    @Column(name = "oe050")
    private String oe050;
    @Column(name = "oe060")
    private String oe060;
    @Column(name = "oe070")
    private String oe070;
    @Column(name = "oe080")
    private String oe080;
    @Column(name = "oe090")
    private String oe090;
    @Column(name = "oe100")
    private String oe100;
    @Column(name = "oe120")
    private String oe120;
    @Column(name = "oe130")
    private String oe130;
    @Column(name = "oe150")
    private String oe150;
    @Column(name = "oe160")
    private String oe160;
    @Column(name = "oe170")
    private String oe170;
    @Column(name = "oe180")
    private String oe180;
    @Column(name = "oe190")
    private String oe190;
    @Column(name = "oe190_comment")
    private String oe190Comment;
    @Column(name = "sq010")
    private String sq010;
    @Column(name = "dq010")
    private String dq010;
    @Column(name = "dq020")
    private String dq020;
    @Column(name = "dq040")
    private String dq040;
    @Column(name = "dq050")
    private String dq050;
    @Column(name = "status_line")
    private String statusLine;
    @Column(name = "check_line_report")
    private String checkLineReport;

}
