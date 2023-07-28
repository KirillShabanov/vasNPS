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
package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasCalendarModelDTO;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VasRedirectedCalendarService {

    public final VasCalendarKiaService vasCalendarKiaService;
    public final VasCalendarJacService vasCalendarJacService;

    private static final Logger LOGGER = Logger.getLogger(VasRedirectedCalendarService.class.getName());

    @Autowired
    public VasRedirectedCalendarService(VasCalendarKiaService vasCalendarKiaService,
                                        VasCalendarJacService vasCalendarJacService) {
        this.vasCalendarKiaService = vasCalendarKiaService;
        this.vasCalendarJacService = vasCalendarJacService;
    }


    public void redirectedCalendar(VasCalendarModelDTO vasCalendarModelDTO){

        if (vasCalendarModelDTO.getBrand().equals("Kia")){
            vasCalendarKiaService.newRowForCalendarKia(vasCalendarModelDTO);
        } else if (vasCalendarModelDTO.getBrand().equals("Jac")) {
            vasCalendarJacService.newRowForCalendarJac(vasCalendarModelDTO);
        } else {
            LOGGER.log(Level.INFO, "Календарь клиента не заведен под данный бренд. "
                    + vasCalendarModelDTO.getBrand()
                    + ". Номер заказ-наряда: "
                    + vasCalendarModelDTO.getNumOrder());
            System.out.println("Календарь клиента не заведен под данный бренд. "
                    + vasCalendarModelDTO.getBrand()
                    + ". Номер заказ-наряда: "
                    + vasCalendarModelDTO.getNumOrder());
        }

    }
}