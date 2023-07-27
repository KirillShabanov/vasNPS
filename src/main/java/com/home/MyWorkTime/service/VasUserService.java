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
package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasUserModel;
import com.home.MyWorkTime.entity.VasUserModelDTO;
import com.home.MyWorkTime.exception.ValidationException;

import java.util.List;
import java.util.Optional;

public interface VasUserService {

    VasUserModelDTO saveUser(VasUserModelDTO vasUserModelDTO) throws ValidationException;

    Optional<VasUserModel> findById(Long id);

    List<VasUserModelDTO> findAll();

    void deleteUser(Long id);
}
