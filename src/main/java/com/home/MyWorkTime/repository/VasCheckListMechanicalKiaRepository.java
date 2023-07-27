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


    @Query(value = "SELECT COUNT(id) FROM vas_check_list_mechanical_kia " +
            "WHERE MONTH(date_save_repair_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_repair_inspection) = YEAR(NOW()) " +
            "AND in_work_check_repair_kia = 'Cancel' ", nativeQuery = true)
    long analyticsKiaMechanicalCountReportCancel();

    @Query(value = "SELECT COUNT(id) FROM vas_check_list_mechanical_kia " +
            "WHERE MONTH(date_save_repair_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_repair_inspection) = YEAR(NOW()) " +
            "AND in_work_check_repair_kia = 'NotCancel' ", nativeQuery = true)
    long analyticsKiaMechanicalCountReportNotCancel();




    @Query(value = "SELECT DISTINCT surname_check_repair_kia FROM vas_check_list_mechanical_kia " +
            "WHERE MONTH(date_save_repair_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_repair_inspection) = YEAR(NOW()) " +
            "AND in_work_check_repair_kia = 'Cancel' ", nativeQuery = true)
    String[] analyticsKiaMechanicalGeneralIndicatorSurname();

    @Query(value = "SELECT COUNT(id) FROM vas_check_list_mechanical_kia " +
            "WHERE surname_check_repair_kia = :s " +
            "AND MONTH(date_save_repair_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_repair_inspection) = YEAR(NOW()) " +
            "AND in_work_check_repair_kia = 'Cancel' ", nativeQuery = true)
    int analyticsKiaMechanicalGeneralIndicatorCountReport(String s);

    @Query(value = "SELECT SUM(result) FROM vas_check_list_mechanical_kia " +
            "WHERE surname_check_repair_kia = :s " +
            "AND MONTH(date_save_repair_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_repair_inspection) = YEAR(NOW()) " +
            "AND in_work_check_repair_kia = 'Cancel' ", nativeQuery = true)
    int numberOfPoints(String s);

    @Query(value = "SELECT * FROM vas_check_list_mechanical_kia WHERE in_work_check_repair_kia = 'Cancel' " +
            "AND MONTH(date_save_repair_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_repair_inspection) = YEAR(NOW()) ", nativeQuery = true)
    List<VasCheckListMechanicalKiaModel> findAllCheckListCancel();

    @Query(value = "SELECT * FROM vas_check_list_mechanical_kia WHERE num_order_check_repair_kia = :numOrderCheckKia " +
            "AND in_work_check_repair_kia = 'Cancel' " +
            "AND MONTH(date_save_repair_inspection) = MONTH(NOW()) " +
            "AND YEAR(date_save_repair_inspection) = YEAR(NOW()) ", nativeQuery = true)
    List<VasCheckListMechanicalKiaModel> findAllCheckListCancelFromNum(@Param("numOrderCheckKia") String numOrderCheckKia);

    @Query(value = "SELECT * FROM vas_check_list_mechanical_kia " +
            "WHERE surname_check_repair_kia LIKE :periodCheckListMechanicalSurname " +
            "AND date_save_repair_inspection BETWEEN :periodCheckListMechanicalDateFrom AND :periodCheckListMechanicalDateBy " +
            "AND in_work_check_repair_kia = 'Cancel' ", nativeQuery = true)
    List<VasCheckListMechanicalKiaModel> countReportsKiaMechanicalPeriod(String periodCheckListMechanicalSurname, String periodCheckListMechanicalDateFrom, String periodCheckListMechanicalDateBy);
}
