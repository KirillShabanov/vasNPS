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

import com.home.MyWorkTime.entity.VasCalendarHavalModel;
import com.home.MyWorkTime.service.VasCalendarHavalService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vas_calendar_client_haval")
@AllArgsConstructor
@CrossOrigin
public class CalendarClientHavalController {

    private final VasCalendarHavalService vasCalendarHavalService;

    @GetMapping("/findThisMonth")
    public List<VasCalendarHavalModel> findThisMonth(){
        return vasCalendarHavalService.findThisMonth();
    }

    @GetMapping("/findPreviousMonth")
    public List<VasCalendarHavalModel> findPreviousMonth(){
        return vasCalendarHavalService.findPreviousMonth();
    }
    
    @GetMapping("/findThisMonthTO/{id}")
    public List<VasCalendarHavalModel> findThisMonthTO (@PathVariable Long id){
       return vasCalendarHavalService.findThisMonthTO(id);
    }

    @PostMapping("/addRemmark/{idForButton}")
    Object addRemmark(@RequestBody VasCalendarHavalModel vasCalendarHavalModel){
        return vasCalendarHavalService.addRemmark(vasCalendarHavalModel);
    }
}
