package com.home.MyWorkTime.part1.employers.service;

import java.util.List;
import java.util.Optional;

import com.home.MyWorkTime.exception.ValidationException;
import com.home.MyWorkTime.part1.employers.entity.EmployersModel;
import com.home.MyWorkTime.part1.employers.entity.EmployersModelDTO;
import com.home.MyWorkTime.part1.employers.validation.ValidationExceptionEmployers;

public interface EmployersService {

    EmployersModelDTO saveEmployers(EmployersModelDTO employersModelDTO) throws ValidationException, ValidationExceptionEmployers;

    Optional<EmployersModel> findById(Long id);

    List<EmployersModelDTO> findAll();
    
}
