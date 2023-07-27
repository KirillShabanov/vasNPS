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
package com.home.MyWorkTime.repository;

import com.home.MyWorkTime.entity.VasMailNpsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface VasMailNpsRepository extends JpaRepository<VasMailNpsModel, Long> {

    @Query(value = "SELECT * FROM vas_nps " +
            "WHERE mail_date = CURRENT_DATE() " +
            "AND brand = 'Kia' " +
            "AND call_status = 'not call' ", nativeQuery = true)
    ArrayList<VasMailNpsModel> npsListKia();

    @Query(value = "SELECT * FROM vas_nps " +
            "WHERE mail_date = CURRENT_DATE() " +
            "AND brand = 'Skoda' " +
            "AND call_status = 'not call' ", nativeQuery = true)
    ArrayList<VasMailNpsModel> npsListSkoda();

    @Query(value = "SELECT * FROM vas_nps " +
            "WHERE mail_date = CURRENT_DATE() " +
            "AND brand != 'Kia' " +
            "AND brand != 'Skoda' " +
            "AND organisation = 'ВитебскАвтоСити' " +
            "AND call_status = 'not call' ", nativeQuery = true)
    ArrayList<VasMailNpsModel> npsListMultibrand();

}
