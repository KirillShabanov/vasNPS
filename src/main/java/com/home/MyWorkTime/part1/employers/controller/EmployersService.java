/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  
 * 
 *  Date of creation: 29/06/2023
 */

package com.home.MyWorkTime.part1.employers.controller;

import java.util.List;
import java.util.Optional;

import com.home.MyWorkTime.exception.ValidationException;
import com.home.MyWorkTime.part1.employers.entity.EmployersModel;
import com.home.MyWorkTime.part1.employers.entity.EmployersModelDTO;

public interface EmployersService {
    
    EmployersModelDTO saveEmployers(EmployersModelDTO employersModelDTO) throws ValidationException;

    Optional<EmployersModel> findById(Long id);

    List<EmployersModelDTO> findAll();

}
