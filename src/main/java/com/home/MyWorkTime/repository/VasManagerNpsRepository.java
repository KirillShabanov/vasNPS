package com.home.MyWorkTime.repository;

import com.home.MyWorkTime.entity.VasManagerNpsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VasManagerNpsRepository extends JpaRepository<VasManagerNpsModel, String> {

    static VasManagerNpsModel saveNpsCall(VasManagerNpsModel saveNpsCall) {
        return saveNpsCall;
    }

    @Query(value = "SELECT * FROM vas_manager WHERE position = 'NPS call' " +
            "AND brand = 'KIA' ", nativeQuery = true)
    List<VasManagerNpsModel> forMailKia();

    @Query(value = "SELECT * FROM vas_manager WHERE position = 'NPS call' " +
            "AND brand = 'SKODA' ", nativeQuery = true)
    List<VasManagerNpsModel> forMailSkoda();

    @Query(value = "SELECT * FROM vas_manager WHERE position = 'NPS call' " +
            "AND brand NOT LIKE 'KIA' " +
            "AND brand NOT LIKE 'SKODA' ", nativeQuery = true)
    List<VasManagerNpsModel> forMailMultibrand();

    @Query(value = "SELECT * FROM vas_manager WHERE position = 'NPS call copy' ", nativeQuery = true)
    List<VasManagerNpsModel> forMailCopy();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM vas_manager WHERE id = :id ", nativeQuery = true)
    void deleteNpsTo(@Param("id") Long id);

    @Query(value = "SELECT * FROM vas_manager WHERE position = 'NPS call' ", nativeQuery = true)
    List<VasManagerNpsModel> forMailTo();
}
