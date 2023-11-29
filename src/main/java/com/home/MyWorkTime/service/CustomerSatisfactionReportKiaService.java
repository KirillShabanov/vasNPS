package com.home.MyWorkTime.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.home.MyWorkTime.entity.CustomerSatisfactionReportKiaModel;
import com.home.MyWorkTime.entity.VasNpsModel;
import com.home.MyWorkTime.repository.CustomerSatisfactionReportKiaRepository;
import com.home.MyWorkTime.repository.VasNpsRepository;

@Service
public class CustomerSatisfactionReportKiaService {
    

    private final CustomerSatisfactionReportKiaRepository customerSatisfactionReportKiaRepository;
    private final VasNpsRepository vasNpsRepository;

    public CustomerSatisfactionReportKiaService(CustomerSatisfactionReportKiaRepository customerSatisfactionReportKiaRepository,
                                                VasNpsRepository vasNpsRepository) {
        this.customerSatisfactionReportKiaRepository = customerSatisfactionReportKiaRepository;
        this.vasNpsRepository = vasNpsRepository;
    }

    private static final Logger LOGGER = Logger.getLogger(CustomerSatisfactionReportKiaService.class.getName());

    @Scheduled(cron = "1 30 09 * * *")
    private void addLineToDatabase() {

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime forSms = localDateTime.minusDays(5);
        Date dateForSMS = Date.from(forSms.atZone(ZoneId.systemDefault()).toInstant());
        Date smsSendingDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        
        List<VasNpsModel> listNpsModel = vasNpsRepository.getDataForBase(dateForSMS);

        if(!(listNpsModel).isEmpty()) {
            for(int i=0; i < listNpsModel.size(); i++) {

                CustomerSatisfactionReportKiaModel customerSatisfactionReportKiaModel = new CustomerSatisfactionReportKiaModel();

                customerSatisfactionReportKiaModel.setNumOrder(listNpsModel.get(i).getNum_order());
                customerSatisfactionReportKiaModel.setVin(listNpsModel.get(i).getVehicle_identification_number());
                customerSatisfactionReportKiaModel.setSmsSendingDate(smsSendingDate);
                customerSatisfactionReportKiaModel.setPhone(listNpsModel.get(i).getPhone_1());
                customerSatisfactionReportKiaModel.setIdClient(listNpsModel.get(i).getIdClient());
                customerSatisfactionReportKiaModel.setSq010("2");
                customerSatisfactionReportKiaModel.setStatusLine("in progress");

                customerSatisfactionReportKiaRepository.save(customerSatisfactionReportKiaModel);
            }
            
            LOGGER.log(Level.INFO, "Адресаты для рассылки ссылки на опрос по SMS бренд KIA добавлены в базу.");
        } else {
            LOGGER.log(Level.INFO, "Адресатов для рассылки ссылки на опрос по SMS бренд KIA НЕТ.");
        }
    }
    
}
