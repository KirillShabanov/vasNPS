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

import java.time.format.DateTimeFormatter;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VasNpsModelDTO {

    private String clientFullName;
    private String[] phone1;
    private String[] vin;
    private String regNum;
    private String model;
    private String yearRelease;
    private String dateOrderClose;
    private String numOrder;
    private String mileage;
    private String category;
    private String masterName;
    private String callDate;
    private String adminName;
    private String adminComment;
    private int nps;
    private String calendarClient;
    private String idClient;
    private String dateSale;

    public String getDateOrderClose() {
        return dateOrderClose.formatted(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getCallDate() {
        return callDate.formatted(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getDateSale() {
        return dateSale.formatted(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

}
