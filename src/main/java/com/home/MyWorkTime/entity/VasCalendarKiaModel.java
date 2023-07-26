package com.home.MyWorkTime.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "vas_calendar_client_kia")
@NoArgsConstructor
@AllArgsConstructor
public class VasCalendarKiaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner")
    private String owner;
    @Column(name = "model")
    private String model;
    @Column(name = "vehicle_identification_number")
    private String vin;
    @Column(name = "year_release")
    private String yearRelease;
    @Column(name = "date_sale")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateSale;
    @Column(name = "phone")
    private String phone;
    @Column(name = "planned_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date plannedDate;
    @Column(name = "to0_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date toDateZero;
    @Column(name = "to0_mileage")
    private Long toMileageZero;
    @Column(name = "to1_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date toDateOne;
    @Column(name = "to1_mileage")
    private Long toMileageOne;
    @Column(name = "to2_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date toDateTwo;
    @Column(name = "to2_mileage")
    private Long toMileageTwo;
    @Column(name = "to3_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date toDateThree;
    @Column(name = "to3_mileage")
    private Long toMileageThree;
    @Column(name = "to4_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date toDateFour;
    @Column(name = "to4_mileage")
    private Long toMileageFour;
    @Column(name = "to5_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date toDateFive;
    @Column(name = "to5_mileage")
    private Long toMileageFive;
    @Column(name = "to6_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date toDateSex;
    @Column(name = "to6_mileage")
    private Long  toMileageSex;
    @Column(name = "to7_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date toDateSeven;
    @Column(name = "to7_mileage")
    private Long toMileageSeven;
    @Column(name = "to8_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date toDateEight;
    @Column(name = "to8_mileage")
    private Long toMileageEight;
    @Column(name = "to9_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date toDateNine;
    @Column(name = "to9_mileage")
    private Long toMileageNine;
    @Column(name = "to10_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date toDateTen;
    @Column(name = "to10_mileage")
    private Long toMileageTen;
    @Column(name = "add_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date addDate;
    @Column(name = "update_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date updateDate;
    @Column(name = "activity")
    private String activity;
    @Column(name = "date_of_interaction")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    public Date date_of_interaction;
    @Column(name = "comment")
    private String comment;
    @Column(name = "master_name")
    private String masterName;
    @Column(name = "remmark")
    private String remmark;
    @Column(name = "date_remmark")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    public Date date_remmark;

}
