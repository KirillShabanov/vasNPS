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

import com.home.MyWorkTime.entity.VasFullReportNpsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface VasFullReportNpsRepository extends JpaRepository<VasFullReportNpsModel,Long> {

    @Query(value = "SELECT COUNT(id) FROM vas_full_report_nps " +
            "WHERE MONTH(outgoing_call_date) = MONTH(NOW()) " +
            "AND YEAR(outgoing_call_date) = YEAR(NOW()) ", nativeQuery = true)
    long countReport();

    static VasFullReportNpsModel saveReportKia(VasFullReportNpsModel saveReportKia){
        return saveReportKia;
    }

    @Query(value = "SELECT * FROM vas_full_report_nps " +
            "WHERE MONTH(outgoing_call_date) = MONTH(NOW())" +
            "AND YEAR(outgoing_call_date) = YEAR(NOW()) ", nativeQuery = true)
    List<VasFullReportNpsModel> fullReportKiaForThisMonth();
}
