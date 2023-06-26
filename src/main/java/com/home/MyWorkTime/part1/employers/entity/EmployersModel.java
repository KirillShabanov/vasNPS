/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  Database empolyers
 * 
 *  Date of creation: 26/06/2023
 */

package com.home.MyWorkTime.part1.employers.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="vas_employers")
@Getter
@Setter
@ToString
public class EmployersModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "subdivision")
    private String subdivision;
    @Column(name = "job_title")
    private String jobTitle;
    @Column(name = "schedule")
    private String schedule;
    @Column(name = "bid")
    private int bid;
    @Column(name = "code_level_access")
    private int codeLevelAccess;
    @Column(name = "authorization_code")
    private String authorizationCode;
    @Column(name = "key_id")
    private String keyId;
    @Column(name = "status")
    private String status;
}
