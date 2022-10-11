package com.home.MyWorkTime.repository;

import com.home.MyWorkTime.entity.VasMailNpsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface VasMailNpsRepository extends JpaRepository<VasMailNpsModel, Long> {

    @Query(value = "SELECT * FROM vas_nps " +
            "WHERE call_date = CURRENT_DATE() " +
            "AND brand = 'KIA' " +
            "AND call_status = 'not call' ", nativeQuery = true)
    ArrayList<VasMailNpsModel> npsListKia();

    @Query(value = "SELECT * FROM vas_nps " +
            "WHERE call_date = CURRENT_DATE() " +
            "AND brand = 'SKODA' " +
            "AND call_status = 'not call' ", nativeQuery = true)
    ArrayList<VasMailNpsModel> npsListSkoda();

    @Query(value = "SELECT * FROM vas_nps " +
            "WHERE call_date = CURRENT_DATE() " +
            "AND brand != 'KIA' " +
            "AND brand != 'SKODA' " +
            "AND call_status = 'not call' ", nativeQuery = true)
    ArrayList<VasMailNpsModel> npsListMultibrand();
}
