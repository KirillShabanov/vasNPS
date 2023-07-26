package com.home.MyWorkTime.service;

import java.util.List;
import java.util.Optional;

import com.home.MyWorkTime.entity.EmployersModel;
import com.home.MyWorkTime.entity.EmployersModelDTO;
import com.home.MyWorkTime.exception.ValidationException;
import com.home.MyWorkTime.exception.ValidationExceptionEmployers;

public interface EmployersService {

    EmployersModelDTO saveEmployer(EmployersModelDTO employersModelDTO) throws ValidationException, ValidationExceptionEmployers;

    Optional<EmployersModel> findById(Long id);

    List<EmployersModelDTO> findAll();

    String checkCodeLevelAccess(String checkCodeLevelAccess);

    String findFullName(String checkCodeLevelAccess);
    
}
