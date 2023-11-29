package com.home.MyWorkTime.service;

import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.home.MyWorkTime.entity.CustomerSatisfactionReportKiaModel;

@Service
public class CustomerSatisfactionReportKiaService {

    private final CustomerSatisfactionReportKiaModel customerSatisfactionReportKiaModel;

    public CustomerSatisfactionReportKiaService(CustomerSatisfactionReportKiaModel customerSatisfactionReportKiaModel) {
        this.customerSatisfactionReportKiaModel = customerSatisfactionReportKiaModel;
    }

    private static final Logger LOGGER = Logger.getLogger(CustomerSatisfactionReportKiaService.class.getName());

    @Scheduled(cron = "1 30 11 * * *")
    private void addLineToDatabase(){

        Date dateForSMS = new Date();
        System.out.println(dateForSMS);
        
        //List<VasNpsModel> listNpsModel = vasNpsRepository.getDataForBase();

        //System.out.println(listNpsModel);
        
        LOGGER.log(Level.INFO, "Адресаты для рассылки ссылки на опрос по SMS бренд KIA добавлены в базу.");
    }
    
}
