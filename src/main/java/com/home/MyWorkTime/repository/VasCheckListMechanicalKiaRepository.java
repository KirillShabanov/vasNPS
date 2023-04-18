package com.home.MyWorkTime.repository;

import com.home.MyWorkTime.entity.VasCheckListMechanicalKiaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VasCheckListMechanicalKiaRepository extends JpaRepository<VasCheckListMechanicalKiaModel, Long> {

    static VasCheckListMechanicalKiaModel saveCheckListMechanicalKia(VasCheckListMechanicalKiaModel saveCheckListMechanicalKia){
        return saveCheckListMechanicalKia;
    }

    @Query(value = "SELECT * FROM vas_check_list_mechanical_kia WHERE in_work_check_repair_kia = 'NotCancel' ", nativeQuery = true)
    List<VasCheckListMechanicalKiaModel> findAllCheckListNotCancelMechanical();

    @Query(value = "SELECT * FROM vas_check_list_mechanical_kia WHERE num_order_check_repair_kia = :numOrderCheckKia " +
            "AND in_work_check_repair_kia = 'NotCancel' ", nativeQuery = true)
    List<VasCheckListMechanicalKiaModel> findAllCheckListNotCancelFromNumMechanical(@Param("numOrderCheckKia") String numOrderCheckKia);
}
