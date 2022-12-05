package com.home.MyWorkTime.repository;

import com.home.MyWorkTime.entity.NullCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NullCategoryRepository extends JpaRepository<NullCategory, Long> {

    @Query(value =  "SELECT category FROM null_category ", nativeQuery = true)
    String[] nullCategoryFromBase();
}
