package com.home.MyWorkTime.repository;

import com.home.MyWorkTime.entity.NullCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NullCategoryRepository extends JpaRepository<NullCategoryModel, Long> {

    @Query(value =  "SELECT category FROM null_category ", nativeQuery = true)
    String[] nullCategoryFromBase();
}
