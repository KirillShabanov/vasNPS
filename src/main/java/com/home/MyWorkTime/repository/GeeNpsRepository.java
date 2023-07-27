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

import com.home.MyWorkTime.entity.GeeNpsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


public interface GeeNpsRepository extends JpaRepository<GeeNpsModel, Long> {

    static GeeNpsModel saveOrderGee(GeeNpsModel savedOrderGee) {
        return savedOrderGee;
    }

    @Modifying
    @Transactional
    @Query(value = "UPDATE gee_nps SET mail_date = :mailDate, admin_name = :adminName WHERE num_order = :numOrder ", nativeQuery = true)
    void updateCallDateGee(@Param("numOrder") String numOrder,
                        @Param("mailDate") Date mailDate,
                        @Param("adminName") String adminName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE gee_nps SET nps = :nps, admin_comment = :adminComment, admin_name = :adminName, call_status = :callStatus, outgoing_call_date = :outgoingCallDate WHERE num_order = :numOrder ", nativeQuery = true)
    void outgoingCallGee(@Param("numOrder") String numOrder,
                      @Param("nps") int nps,
                      @Param("adminComment") String adminComment,
                      @Param("adminName") String adminName,
                      @Param("callStatus") String callStatus,
                      @Param("outgoingCallDate") Date outgoingCallDate);

    @Query(value =  "SELECT id FROM gee_nps " +
            "WHERE num_order = :numOrderGee ", nativeQuery = true)
    String checkedNumOrderGee(String numOrderGee);
}
