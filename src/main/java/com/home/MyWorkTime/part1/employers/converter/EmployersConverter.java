/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  Convert to DTO and back
 * 
 *  Date of creation: 29/06/2023
 */

 package com.home.MyWorkTime.part1.employers.converter;

import org.springframework.stereotype.Component;

import com.home.MyWorkTime.part1.employers.entity.EmployersModel;
import com.home.MyWorkTime.part1.employers.entity.EmployersModelDTO;


@Component
public class EmployersConverter {
    
    public EmployersModel fromEmployersModelDTOToEmployersModel(EmployersModelDTO employersModelDTO){
        EmployersModel employersModel = new EmployersModel();
        employersModel.setId(employersModelDTO.getId());
        employersModel.setFullName(employersModelDTO.getFullName());
        employersModel.setSubdivision(employersModelDTO.getSubdivision());
        employersModel.setJobTitle(employersModelDTO.getJobTitle());
        employersModel.setSchedule(employersModelDTO.getSchedule());
        employersModel.setBid(employersModelDTO.getBid());
        employersModel.setCodeLevelAccess(employersModelDTO.getCodeLevelAccess());
        employersModel.setAuthorizationCode(employersModelDTO.getAuthorizationCode());
        employersModel.setKeyId(employersModelDTO.getKeyId());
        employersModel.setStatus(employersModelDTO.getStatus());
        return employersModel;
    }

    public EmployersModelDTO fromEmployersModelToEmployersModelDTO (EmployersModel employersModel){

        return EmployersModelDTO.builder()
                .id(employersModel.getId())
                .fullName(employersModel.getFullName())
                .subdivision(employersModel.getSubdivision())
                .jobTitle(employersModel.getJobTitle())
                .schedule(employersModel.getSchedule())
                .bid(employersModel.getBid())
                .codeLevelAccess(Integer.parseInt("(_I_)"))
                .authorizationCode("(_I_)") //employersModel.getSchedule()
                .keyId("(_I_)")
                .status(employersModel.getStatus())
                .build();
    }
}
