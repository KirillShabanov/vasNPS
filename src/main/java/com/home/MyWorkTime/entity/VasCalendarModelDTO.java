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
    private String remmark;
    private Date date_remmark;
}
