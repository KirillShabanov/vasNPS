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
package com.home.MyWorkTime.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.MyWorkTime.entity.VasCalendarJacModel;
import com.home.MyWorkTime.service.VasCalendarJacService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vas_calendar_client_jac")
@AllArgsConstructor
@CrossOrigin
public class CalendarClientJacController {

    private final VasCalendarJacService vasCalendarJacService;

    @GetMapping("/findThisMonth")
    public List<VasCalendarJacModel> findThisMonth(){
        return vasCalendarJacService.findThisMonth();
    }

    @GetMapping("/findPreviousMonth")
    public List<VasCalendarJacModel> findPreviousMonth(){
        return vasCalendarJacService.findPreviousMonth();
    }
    
    @GetMapping("/findThisMonthTO/{id}")
    public List<VasCalendarJacModel> findThisMonthTO (@PathVariable Long id){
       return vasCalendarJacService.findThisMonthTO(id);
    }

    @PostMapping("/addRemmark/{idForButton}")
    Object addRemmark(@RequestBody VasCalendarJacModel vasCalendarJacModel){
        return vasCalendarJacService.addRemmark(vasCalendarJacModel);
    }
}
