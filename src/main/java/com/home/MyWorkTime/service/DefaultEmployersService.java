/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  Service
 * 
 *  Date of creation: 29/06/2023
 */

package com.home.MyWorkTime.service;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Level;
import org.springframework.stereotype.Service;

import com.home.MyWorkTime.converter.EmployersConverter;
import com.home.MyWorkTime.entity.EmployersModel;
import com.home.MyWorkTime.entity.EmployersModelDTO;
import com.home.MyWorkTime.exception.ValidationExceptionEmployers;
import com.home.MyWorkTime.repository.EmployersRepository;


@Slf4j
@Service
public class DefaultEmployersService implements EmployersService {

    private static final Logger LOGGER = Logger.getLogger(DefaultEmployersService.class.getName());

    private final EmployersRepository employersRepository;
    private final EmployersConverter employersConverter;

    public DefaultEmployersService(EmployersRepository employersRepository, 
                                    EmployersConverter employersConverter){
        this.employersRepository = employersRepository;
        this.employersConverter = employersConverter;
    }

    @Override
    public EmployersModelDTO saveEmployer(EmployersModelDTO employersModelDTO) throws ValidationExceptionEmployers {
        validateEmployersModelDTO(employersModelDTO);

        EmployersModel savedEmployers = employersRepository.save(employersConverter.fromEmployersModelDTOToEmployersModel(employersModelDTO));
        return employersConverter.fromEmployersModelToEmployersModelDTO(savedEmployers);
    }

    private void validateEmployersModelDTO(EmployersModelDTO employersModelDTO) throws ValidationExceptionEmployers {
        if (isNull(employersModelDTO)){
            throw new ValidationExceptionEmployers("Object user is null");
        }
    }

    @Override
    public List<EmployersModelDTO> findAll() {
        return employersRepository.findAll()
                .stream()
                .map(employersConverter::fromEmployersModelToEmployersModelDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmployersModel> findById(Long id) {
        return employersRepository.findById(id);
    }

    @Override
    public String checkCodeLevelAccess(String checkCodeLevelAccess) {
        if ((employersRepository.checkCodeLevelAccess(checkCodeLevelAccess) == null)){
            return "null";
        } else {
            return employersRepository.checkCodeLevelAccess(checkCodeLevelAccess);
        }
    }

    @Override
    public String findFullName(String checkCodeLevelAccess) {
        return employersRepository.findFullName(checkCodeLevelAccess);
    }

}
