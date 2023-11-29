package com.home.MyWorkTime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.MyWorkTime.entity.CustomerSatisfactionReportKiaModel;

public interface CustomerSatisfactionReportKiaRepository extends JpaRepository<CustomerSatisfactionReportKiaModel, Long> {
    
}
