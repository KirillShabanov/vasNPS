package com.home.MyWorkTime.repository;

import com.home.MyWorkTime.entity.VasCalendarKiaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;



public interface VasCalendarKiaRepository extends JpaRepository<VasCalendarKiaModel, Long> {

    @Query(value = "SELECT vehicle_identification_number FROM vas_calendar_client_kia WHERE vehicle_identification_number = :vin ", nativeQuery = true)
    String findVin(String vin);

    @Query(value = "SELECT * FROM vas_calendar_client_kia WHERE vehicle_identification_number = :vin ", nativeQuery = true)
    VasCalendarKiaModel findCardByVin(String vin);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vas_calendar_client_kia SET planned_date = :planned_date, to0_date = :to0_date, to0_mileage = :to0_mileage WHERE vehicle_identification_number = :vin ", nativeQuery = true)
    void updateToZero(@Param("vin") String vin,
                      @Param("to0_date") Date to0_date,
                      @Param("to0_mileage") Long to0_mileage,
                      @Param("planned_date") Date plannedDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vas_calendar_client_kia SET planned_date = :planned_date, to1_date = :to1_date, to1_mileage = :to1_mileage WHERE vehicle_identification_number = :vin ", nativeQuery = true)
    void updateToOne(@Param("vin") String vin,
                      @Param("to1_date") Date to1_date,
                      @Param("to1_mileage") Long to1_mileage,
                      @Param("planned_date") Date plannedDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vas_calendar_client_kia SET planned_date = :planned_date, to2_date = :to2_date, to2_mileage = :to2_mileage WHERE vehicle_identification_number = :vin ", nativeQuery = true)
    void updateToTwo(@Param("vin") String vin,
                     @Param("to2_date") Date to2_date,
                     @Param("to2_mileage") Long to2_mileage,
                     @Param("planned_date") Date plannedDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vas_calendar_client_kia SET planned_date = :planned_date, to3_date = :to3_date, to3_mileage = :to3_mileage WHERE vehicle_identification_number = :vin ", nativeQuery = true)
    void updateToThree(@Param("vin") String vin,
                        @Param("to3_date") Date to3_date,
                        @Param("to3_mileage") Long to3_mileage,
                        @Param("planned_date") Date plannedDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vas_calendar_client_kia SET planned_date = :planned_date, to4_date = :to4_date, to4_mileage = :to4_mileage WHERE vehicle_identification_number = :vin ", nativeQuery = true)
    void updateToFour(@Param("vin") String vin,
                       @Param("to4_date") Date to4_date,
                       @Param("to4_mileage") Long to4_mileage,
                       @Param("planned_date") Date plannedDate);


}
