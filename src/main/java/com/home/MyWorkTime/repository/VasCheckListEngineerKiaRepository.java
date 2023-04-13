package com.home.MyWorkTime.repository;

import com.home.MyWorkTime.entity.VasCheckListEngineerKiaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VasCheckListEngineerKiaRepository extends JpaRepository<VasCheckListEngineerKiaModel, Long> {

    static VasCheckListEngineerKiaModel saveCheckListEngineerKia(VasCheckListEngineerKiaModel saveCheckListEngineerKia){
        return saveCheckListEngineerKia;
    }

    @Query(value = "SELECT * FROM vas_check_list_engineer_kia WHERE num_order_check_kia = :numOrder ", nativeQuery = true)
    static long searchNumOrder(long numOrder) {
        return numOrder;
    }

    @Query(value = "SELECT * FROM vas_check_list_engineer_kia WHERE in_work = 'NotCancel' ", nativeQuery = true)
    List<VasCheckListEngineerKiaModel> findAllCheckListNotCancel();

    @Query(value = "SELECT * FROM vas_check_list_engineer_kia WHERE num_order_check_kia = :numOrderCheckKia " +
            "AND in_work = 'NotCancel' ", nativeQuery = true)
    List<VasCheckListEngineerKiaModel> findAllCheckListNotCancelFromNum(@Param("numOrderCheckKia") String numOrderCheckKia);
}
