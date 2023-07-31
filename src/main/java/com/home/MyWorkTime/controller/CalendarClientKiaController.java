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

import com.home.MyWorkTime.entity.VasCalendarKiaModel;
import com.home.MyWorkTime.service.VasCalendarKiaService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vas_calendar_client_kia")
@AllArgsConstructor
@CrossOrigin
public class CalendarClientKiaController {

    private final VasCalendarKiaService vasCalendarKiaService;

    @GetMapping("/findThisMonth")
    public List<VasCalendarKiaModel> findThisMonth(){
        return vasCalendarKiaService.findThisMonth();
    }
    @GetMapping("/findPreviousMonth")
    public List<VasCalendarKiaModel> findPreviousMonth(){
        return vasCalendarKiaService.findPreviousMonth();
    }
    
    @GetMapping("/findThisMonthTO/{id}")
    public List<VasCalendarKiaModel> findThisMonthTO (@PathVariable Long id){
       return vasCalendarKiaService.findThisMonthTO(id);
    }

    @PostMapping("/addRemmark/{idForButton}")
    Object addRemmark(@RequestBody VasCalendarKiaModel vasCalendarKiaModel){
        return vasCalendarKiaService.addRemmark(vasCalendarKiaModel);
    }
}
