/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  Controller empolyers
 * 
 *  Date of creation: 26/06/2023
 */

 package com.home.MyWorkTime.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//import com.home.MyWorkTime.converter.EmployersConverter;
import com.home.MyWorkTime.entity.EmployersModelDTO;
import com.home.MyWorkTime.exception.ValidationException;
import com.home.MyWorkTime.exception.ValidationExceptionEmployers;
import com.home.MyWorkTime.service.EmployersService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vas_employers")
@AllArgsConstructor
@CrossOrigin
public class EmployersController {

    private final EmployersService employersService;
    //private final EmployersConverter employersConverter;

    private static final Logger LOGGER = Logger.getLogger(EmployersController.class.getName());

    @GetMapping("/findAll")
    public List<EmployersModelDTO> findAllEmployers(){
        return employersService.findAll();
    }

    @GetMapping("/checkCodeLevelAccess/{checkCodeLevelAccess}")
    String CheckCodeLevelAccess(@PathVariable String checkCodeLevelAccess){
        return employersService.checkCodeLevelAccess(checkCodeLevelAccess);
    }

    @PostMapping("/addEmployer&{checkCodeLevelAccess}")
    public EmployersModelDTO saveEmployers(@RequestBody EmployersModelDTO employersModelDTO,
                                            @PathVariable String checkCodeLevelAccess) throws ValidationExceptionEmployers, ValidationException {
                                                
        String addName = employersService.findFullName(checkCodeLevelAccess);
        String nameEmployer = employersModelDTO.getFullName();
        LOGGER.log(Level.INFO, addName + " добавил сотрудника: " + nameEmployer + ".");

        return employersService.saveEmployer(employersModelDTO);
    }
}
