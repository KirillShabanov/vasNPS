/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  Changing data from the database on transfer
 * 
 *  Date of creation: 29/06/2023
 */

package com.home.MyWorkTime.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class EmployersModelDTO {
    
    private Long id;
    private String fullName;
    private String subdivision;
    private String jobTitle;
    private String schedule;
    private double bid;
    private int codeLevelAccess;
    private String authorizationCode;
    private String keyId;
    private String status;
    private String email;
}
