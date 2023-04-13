package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasCalendarModelDTO;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class VasRedirectedCalendarService {

    public final VasCalendarKiaService vasCalendarKiaService;

    private static final Logger LOGGER = Logger.getLogger(VasRedirectedCalendarService.class.getName());

    public VasRedirectedCalendarService(VasCalendarKiaService vasCalendarKiaService) {
        this.vasCalendarKiaService = vasCalendarKiaService;
    }


    public void redirectedCalendar(VasCalendarModelDTO vasCalendarModelDTO){

        if (vasCalendarModelDTO.getBrand().equals("Kia")){
            vasCalendarKiaService.newRowForCalendarKia(vasCalendarModelDTO);
        } else if (vasCalendarModelDTO.getBrand().equals("Skoda")) {
            System.out.println("Прилитела Шкода");
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
