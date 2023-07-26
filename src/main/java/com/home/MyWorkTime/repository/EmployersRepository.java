/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  Description of methods of working with the database
 * 
 *  Date of creation: 26/06/2023
 */

package com.home.MyWorkTime.repository;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.home.MyWorkTime.entity.EmployersModel;


public interface EmployersRepository extends JpaRepository<EmployersModel, Long>{

    @Override
    @NotNull
    Optional<EmployersModel> findById (@NotNull Long id);

    @Query(value = "SELECT code_level_access FROM vas_employers WHERE authorization_code = :checkCodeLevelAccess " +
                    "AND status = 'active' ", nativeQuery = true)
    String checkCodeLevelAccess(String checkCodeLevelAccess);

    @Query(value = "SELECT full_name FROM vas_employers WHERE authorization_code = :checkCodeLevelAccess " +
                    "AND status = 'active' ", nativeQuery = true)
    String findFullName(String checkCodeLevelAccess);
}
