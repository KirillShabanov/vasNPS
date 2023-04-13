package com.home.MyWorkTime.entity;

import lombok.*;
import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VasCalendarModelDTO {

    private String numOrder;
    private String owner;
    private String brand;
    private String model;
    private String vin;
    private String yearRelease;
    private Date sale;
    private String phone;
    private Date dateRepair;
    private Long mileage;
    private String masterName;
}
