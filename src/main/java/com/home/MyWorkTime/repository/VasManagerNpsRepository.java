package com.home.MyWorkTime.repository;

import com.home.MyWorkTime.entity.VasManagerNpsModel;
import com.home.MyWorkTime.entity.VasNpsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface VasManagerNpsRepository extends JpaRepository<VasManagerNpsModel, String> {

    static VasManagerNpsModel saveNpsCall(VasManagerNpsModel saveNpsCall) {
        return saveNpsCall;
    }

    @Query(value = "SELECT * FROM vas_manager WHERE position = 'NPS call' " +
            "AND brand LIKE 'Kia' ", nativeQuery = true)
    List<VasManagerNpsModel> forMailKia();

    @Query(value = "SELECT * FROM vas_manager WHERE position = 'NPS call' " +
            "AND brand LIKE 'Skoda' ", nativeQuery = true)
    List<VasManagerNpsModel> forMailSkoda();

    @Query(value = "SELECT * FROM vas_manager WHERE position = 'NPS call' " +
            "AND brand NOT LIKE 'Kia' " +
            "AND brand NOT LIKE 'Skoda' " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    List<VasManagerNpsModel> forMailMultibrand();

    @Query(value = "SELECT * FROM vas_manager WHERE position = 'NPS call copy' ", nativeQuery = true)
    List<VasManagerNpsModel> forMailCopy();

    @Query(value = "SELECT * FROM vas_manager WHERE position = 'Technical' AND organisation = 'VitebskAutoCity' ", nativeQuery = true)
    List<VasManagerNpsModel> findAllTechnical();

    @Query(value = "SELECT * FROM vas_manager WHERE position = 'BodyRepair' AND organisation = 'VitebskAutoCity' ", nativeQuery = true)
    List<VasManagerNpsModel> findAllBodyRepair();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM vas_manager WHERE id = :id ", nativeQuery = true)
    void deleteNpsTo(@Param("id") Long id);

    @Query(value = "SELECT * FROM vas_manager WHERE position = 'NPS call' AND organisation = 'VitebskAutoCity' ", nativeQuery = true)
    List<VasManagerNpsModel> forMailTo();

    @Query(value =  "SELECT id FROM vas_nps " +
            "WHERE num_order = :numOrder ", nativeQuery = true)
    String checkedNumOrder(String numOrder);

    @Query(value =  "SELECT manager_name FROM vas_manager WHERE position = 'Technical' AND organisation = 'VitebskAutoCity' ", nativeQuery = true)
    String[] gradeNpsNameTech();

    @Query(value =  "SELECT manager_name FROM vas_manager WHERE position = 'BodyRepair' AND organisation = 'VitebskAutoCity' ", nativeQuery = true)
    String[] gradeNpsNameBodyRepair();

    @Query(value =  "SELECT manager_name FROM vas_manager WHERE organisation = 'VitebskAutoCity' ", nativeQuery = true)
    String[] organisationName();

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE master_name =:nameN AND nps > 8 " +
            "AND WEEK(date_order) = WEEK(NOW()) " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    int gradeNpsPromoterWeek(String nameN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE master_name =:nameN AND nps < 7 " +
            "AND WEEK(date_order) = WEEK(NOW()) " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    int gradeNpsCriticWeek(String nameN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE master_name =:nameN AND nps > 0 " +
            "AND WEEK(date_order) = WEEK(NOW()) " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    int gradeNpsAllWeek(String nameN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE master_name =:nameN AND nps > 8 " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    int gradeNpsPromoterMonth(String nameN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE master_name =:nameN AND nps < 7 " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    int gradeNpsCriticMonth(String nameN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE master_name =:nameN AND nps > 0 " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    int gradeNpsAllMonth(String nameN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE department =:departmentN AND nps > 8 " +
            "AND WEEK(date_order) = WEEK(NOW()) " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    int gradeNpsDepartmentPromoterWeek(String departmentN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE department =:departmentN AND nps < 7 " +
            "AND WEEK(date_order) = WEEK(NOW()) " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    int gradeNpsDepartmentCriticWeek(String departmentN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE department =:departmentN AND nps > 0 " +
            "AND WEEK(date_order) = WEEK(NOW()) " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    int gradeNpsDepartmentAllWeek(String departmentN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE department =:departmentN AND nps > 8 " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    int gradeNpsDepartmentPromoterMonth(String departmentN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE department =:departmentN AND nps < 7 " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    int gradeNpsDepartmentCriticMonth(String departmentN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE department =:departmentN AND nps > 0 " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) " +
            "AND organisation = 'ВитебскАвтоСити' ", nativeQuery = true)
    int gradeNpsDepartmentAllMonth(String departmentN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation =:organisationN AND nps > 8 " +
            "AND WEEK(date_order) = WEEK(NOW()) " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) ", nativeQuery = true)
    int gradeNpsOrganisationPromoterWeek(String organisationN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation =:organisationN AND nps < 7 " +
            "AND WEEK(date_order) = WEEK(NOW()) " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) ", nativeQuery = true)
    int gradeNpsOrganisationCriticWeek(String organisationN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation =:organisationN AND nps > 0 " +
            "AND WEEK(date_order) = WEEK(NOW()) " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) ", nativeQuery = true)
    int gradeNpsOrganisationAllWeek(String organisationN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation =:organisationN AND nps > 8 " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) ", nativeQuery = true)
    int gradeNpsOrganisationPromoterMonth(String organisationN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation =:organisationN AND nps < 7 " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) ", nativeQuery = true)
    int gradeNpsOrganisationCriticMonth(String organisationN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation =:organisationN AND nps > 0 " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND YEAR(date_order) = YEAR(NOW()) ", nativeQuery = true)
    int gradeNpsOrganisationAllMonth(String organisationN);

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation = 'ВитебскАвтоСити' " +
            "AND MONTH(date_order) = MONTH(NOW()) ", nativeQuery = true)
    int countCurrentMonthOrder();

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation = 'ВитебскАвтоСити' " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND call_status = 'call' ", nativeQuery = true)
    int countCurrentMonthCloseOrder();

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation = 'ВитебскАвтоСити' " +
            "AND brand LIKE 'Kia' " +
            "AND MONTH(date_order) = MONTH(NOW()) ", nativeQuery = true)
    float countCurrentMonthKiaOrder();

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation = 'ВитебскАвтоСити' " +
            "AND brand LIKE 'Kia' " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND call_status = 'call' ", nativeQuery = true)
    float countCurrentMonthCloseKiaOrder();

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation = 'ВитебскАвтоСити' " +
            "AND brand LIKE 'Skoda' " +
            "AND MONTH(date_order) = MONTH(NOW()) ", nativeQuery = true)
    float countCurrentMonthSkodaOrder();

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation = 'ВитебскАвтоСити' " +
            "AND brand LIKE 'Skoda' " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND call_status = 'call' ", nativeQuery = true)
    float countCurrentMonthCloseSkodaOrder();

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation = 'ВитебскАвтоСити' " +
            "AND brand NOT LIKE 'Kia' " +
            "AND brand NOT LIKE 'Skoda' " +
            "AND MONTH(date_order) = MONTH(NOW()) ", nativeQuery = true)
    float countCurrentMonthMultibrandOrder();

    @Query(value = "SELECT COUNT(id) FROM vas_nps WHERE organisation = 'ВитебскАвтоСити' " +
            "AND brand NOT LIKE 'Kia' " +
            "AND brand NOT LIKE 'Skoda' " +
            "AND MONTH(date_order) = MONTH(NOW()) " +
            "AND call_status = 'call' ", nativeQuery = true)
    float countCurrentMonthCloseMultibrandOrder();

    @Query(value = "SELECT num_order, date_order, master_name, nps FROM vas_nps WHERE organisation = 'ВитебскАвтоСити' " +
            "AND MONTH(date_order) = MONTH(NOW()) ", nativeQuery = true)
    String[][] currentMonthAllOrder();
}
