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

    @Query(value = "SELECT * FROM vas_check_list_engineer_kia WHERE in_work = 'NotCancel' " +
            "AND MONTH(date_save_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_inspection) = YEAR(NOW()) ", nativeQuery = true)
    List<VasCheckListEngineerKiaModel> findAllCheckListNotCancel();

    @Query(value = "SELECT * FROM vas_check_list_engineer_kia WHERE num_order_check_kia = :numOrderCheckKia " +
            "AND in_work = 'NotCancel' " +
            "AND MONTH(date_save_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_inspection) = YEAR(NOW()) ", nativeQuery = true)
    List<VasCheckListEngineerKiaModel> findAllCheckListNotCancelFromNum(@Param("numOrderCheckKia") String numOrderCheckKia);

    @Query(value = "SELECT COUNT(id) FROM vas_check_list_engineer_kia " +
            "WHERE MONTH(date_save_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_inspection) = YEAR(NOW()) " +
            "AND in_work = 'Cancel' ", nativeQuery = true)
    long analyticsKiaEngineerCountReportCancel();

    @Query(value = "SELECT COUNT(id) FROM vas_check_list_engineer_kia " +
            "WHERE MONTH(date_save_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_inspection) = YEAR(NOW()) " +
            "AND in_work = 'NotCancel' ", nativeQuery = true)
    long analyticsKiaEngineerCountReportNotCancel();

    @Query(value = "SELECT DISTINCT surname_engineer_kia FROM vas_check_list_engineer_kia " +
                "WHERE MONTH(date_save_inspection) = MONTH(NOW()) " +
                "AND YEAR(date_save_inspection) = YEAR(NOW()) " +
                "AND in_work = 'Cancel' ", nativeQuery = true)
    String[] analyticsKiaEngineerGeneralIndicatorSurname();

    @Query(value = "SELECT COUNT(id) FROM vas_check_list_engineer_kia " +
                "WHERE surname_engineer_kia = :s " +
                "AND MONTH(date_save_inspection) = MONTH(NOW()) " +
                "AND YEAR(date_save_inspection) = YEAR(NOW()) " +
                "AND in_work = 'Cancel' ", nativeQuery = true)
    int analyticsKiaEngineerGeneralIndicatorCountReport(String s);

    @Query(value = "SELECT SUM(result) FROM vas_check_list_engineer_kia " +
            "WHERE surname_engineer_kia = :s " +
            "AND MONTH(date_save_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_inspection) = YEAR(NOW()) " +
            "AND in_work = 'Cancel' ", nativeQuery = true)
    int numberOfPoints(String s);

    @Query(value = "SELECT * FROM vas_check_list_engineer_kia WHERE in_work = 'Cancel' " +
            "AND MONTH(date_save_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_inspection) = YEAR(NOW()) ", nativeQuery = true)
    List<VasCheckListEngineerKiaModel> findAllCheckListCancel();

    @Query(value = "SELECT * FROM vas_check_list_engineer_kia WHERE num_order_check_kia = :numOrderCheckKia " +
            "AND in_work = 'Cancel' " +
            "AND MONTH(date_save_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_inspection) = YEAR(NOW()) ", nativeQuery = true)
    List<VasCheckListEngineerKiaModel> findAllCheckListCancelFromNum(@Param("numOrderCheckKia") String numOrderCheckKia);
}
