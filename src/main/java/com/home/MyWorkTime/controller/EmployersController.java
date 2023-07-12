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

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.home.MyWorkTime.converter.EmployersConverter;
import com.home.MyWorkTime.entity.EmployersModelDTO;
import com.home.MyWorkTime.service.EmployersService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vas_employers")
@AllArgsConstructor
@CrossOrigin
public class EmployersController {

    private final EmployersService employersService;
    private final EmployersConverter employersConverter;


    @GetMapping("/findAll")
    public List<EmployersModelDTO> findAllEmployers(){
        return employersService.findAll();
    }
    
}
