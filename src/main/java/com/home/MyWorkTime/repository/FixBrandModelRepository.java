package com.home.MyWorkTime.repository;

import com.home.MyWorkTime.entity.FixBrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface FixBrandModelRepository extends JpaRepository<FixBrandModel, String> {

    @Query(value =  "SELECT fix_key, fix_value FROM fix_brand_model ", nativeQuery = true)
    String[] tableFix();
}
