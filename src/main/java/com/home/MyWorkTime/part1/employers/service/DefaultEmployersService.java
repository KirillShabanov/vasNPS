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

package com.home.MyWorkTime.part1.employers.service;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.home.MyWorkTime.part1.employers.converter.EmployersConverter;
import com.home.MyWorkTime.part1.employers.entity.EmployersModel;
import com.home.MyWorkTime.part1.employers.entity.EmployersModelDTO;
import com.home.MyWorkTime.part1.employers.repository.EmployersRepository;
import com.home.MyWorkTime.part1.employers.validation.ValidationExceptionEmployers;

@Service
public class DefaultEmployersService implements EmployersService {

    private final EmployersRepository employersRepository;
    private final EmployersConverter employersConverter;

    public DefaultEmployersService(EmployersRepository employersRepository, 
                                    EmployersConverter employersConverter){
        this.employersRepository = employersRepository;
        this.employersConverter = employersConverter;
    }

    @Override
    public EmployersModelDTO saveEmployers(EmployersModelDTO employersModelDTO) throws ValidationExceptionEmployers {
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

    
}
