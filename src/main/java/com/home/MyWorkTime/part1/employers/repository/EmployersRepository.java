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

package com.home.MyWorkTime.part1.employers.repository;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import com.home.MyWorkTime.part1.employers.entity.EmployersModel;


public interface EmployersRepository extends JpaRepository<EmployersModel, Long>{

    @Override
    @NotNull
    Optional<EmployersModel> findById (@NotNull Long id);
}
