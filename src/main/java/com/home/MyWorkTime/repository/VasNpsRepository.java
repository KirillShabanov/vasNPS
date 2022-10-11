package com.home.MyWorkTime.repository;

import com.home.MyWorkTime.entity.VasNpsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface VasNpsRepository extends JpaRepository<VasNpsModel, Long> {

    static VasNpsModel saveOrder(VasNpsModel savedOrder) {
        return savedOrder;
    }

    @Modifying
    @Transactional
    @Query(value = "UPDATE vas_nps SET call_date = :call_date WHERE num_order = :num_order ", nativeQuery = true)
    void updateCallDate(@Param("num_order") int num_order,
                        @Param("call_date") String call_date);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vas_nps SET nps = :nps, admin_comment = :admin_comment, admin_name = :admin_name, call_status = :call_status WHERE num_order = :num_order ", nativeQuery = true)
    void npsCall(@Param("num_order") int num_order,
                 @Param("nps") int nps,
                 @Param("admin_comment") String admin_comment,
                 @Param("admin_name") String admin_name,
                 @Param("call_status") String call_status);
}
