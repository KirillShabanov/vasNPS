/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  Database level access
 * 
 *  Date of creation: 26/06/2023
 */

package com.home.MyWorkTime.part1.levelAccess.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="vas_level_access")
@Getter
@Setter
@ToString
public class LevelAccessModel {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "level_access")
    private int levelAccess;
    @Column(name = "description_access")
    private String descriptionAccess;
}
