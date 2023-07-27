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

import com.home.MyWorkTime.entity.FixBrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FixBrandModelRepository extends JpaRepository<FixBrandModel, String> {


    @Query(value =  "SELECT fix_key FROM fix_brand_model ", nativeQuery = true)
    String[] keyFix();

    @Query(value =  "SELECT fix_value FROM fix_brand_model ", nativeQuery = true)
    String[] valueFix();
}
