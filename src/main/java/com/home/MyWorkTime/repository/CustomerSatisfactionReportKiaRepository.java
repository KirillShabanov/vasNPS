package com.home.MyWorkTime.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.home.MyWorkTime.entity.CustomerSatisfactionReportKiaModel;

public interface CustomerSatisfactionReportKiaRepository extends JpaRepository<CustomerSatisfactionReportKiaModel, Long> {

    @Query(value = "SELECT * FROM customer_satisfaction_kia " +
    "WHERE DATE(sms_sending_date) = DATE(:dateForSMS) ", nativeQuery = true)
    List<CustomerSatisfactionReportKiaModel> getModelForSms(String dateForSMS);

    @Modifying
    @Transactional
    @Query(value = "UPDATE customer_satisfaction_kia SET id_sms = :messageData " + 
    "WHERE num_order = :numOrder ", nativeQuery = true)
    void addIdSMS(@Param("numOrder") String numOrder, 
                  @Param("messageData") String messageData);
}
