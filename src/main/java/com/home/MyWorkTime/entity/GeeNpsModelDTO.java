package com.home.MyWorkTime.entity;

import lombok.*;

import java.time.format.DateTimeFormatter;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GeeNpsModelDTO {

    private String clientFullName;
    private String[] phone1;
    private String[] vin;
    private String regNum;
    private String model;
    private String yearRelease;
    private String dateOrderClose;
    private String numOrder;
    private Long mileage;
    private String category;
    private String masterName;
    private String callDate;
    private String adminName;
    private String adminComment;
    private int nps;
    private String calendar_client;

    public String getDateOrderClose() {
        return dateOrderClose.formatted(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getCallDate() {
        return callDate.formatted(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

}
