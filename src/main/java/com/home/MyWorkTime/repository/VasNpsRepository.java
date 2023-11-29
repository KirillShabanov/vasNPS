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

import com.home.MyWorkTime.entity.VasNpsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface VasNpsRepository extends JpaRepository<VasNpsModel, Long> {

    static VasNpsModel saveOrder(VasNpsModel savedOrder) {
        return savedOrder;
    }

    @Modifying
    @Transactional
    @Query(value = "UPDATE vas_nps SET mail_date = :mailDate, admin_name = :adminName WHERE num_order = :numOrder ", nativeQuery = true)
    void updateCallDate(@Param("numOrder") String numOrder,
                        @Param("mailDate") Date mailDate,
                        @Param("adminName") String adminName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vas_nps SET nps = :nps, admin_comment = :adminComment, admin_name = :adminName, call_status = :callStatus, outgoing_call_date = :outgoingCallDate WHERE num_order = :numOrder ", nativeQuery = true)
    void outgoingCall(@Param("numOrder") String numOrder,
                      @Param("nps") int nps,
                      @Param("adminComment") String adminComment,
                      @Param("adminName") String adminName,
                      @Param("callStatus") String callStatus,
                      @Param("outgoingCallDate") Date outgoingCallDate);


    @Query(value = "SELECT * FROM vas_nps WHERE num_order = :numOrder " +
            "AND brand LIKE 'Kia' ", nativeQuery = true)
    List<VasNpsModel> searchReport(@Param("numOrder") String numOrder);

    @Modifying
    @Transactional
    @Query(value =  "UPDATE vas_nps SET calendar_client = 'selected' WHERE num_order = :numOrder ", nativeQuery = true)
    void updateValueCalendar(@Param("numOrder") String numOrder);

    
}

