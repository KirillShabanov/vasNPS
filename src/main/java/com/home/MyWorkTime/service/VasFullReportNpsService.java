package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasFullReportNpsModel;
import com.home.MyWorkTime.entity.VasNpsModel;
import com.home.MyWorkTime.repository.VasFullReportNpsRepository;
import com.home.MyWorkTime.repository.VasNpsRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
public class VasFullReportNpsService {

    private VasFullReportNpsRepository vasFullReportNpsRepository;
    private VasNpsRepository vasNpsRepository;

    private static final Logger LOGGER = Logger.getLogger(VasFullReportNpsService.class.getName());

    @Autowired
    public VasFullReportNpsService(VasFullReportNpsRepository vasFullReportNpsRepository, VasNpsRepository vasNpsRepository) {
        this.vasFullReportNpsRepository = vasFullReportNpsRepository;
        this.vasNpsRepository = vasNpsRepository;
    }

    public List<VasNpsModel> searchReport(String numOrder) {
        LOGGER.log(Level.INFO, "Запрос данных для расширенного регламента опроса клиента KIA: " + numOrder);
        System.out.println("Запрос данных для расширенного регламента опроса клиента KIA: " + numOrder);
        return vasNpsRepository.searchReport(numOrder);
    }

    public long countReport() {
        return vasFullReportNpsRepository.countReport();
    }

    public VasFullReportNpsModel saveReportKia(VasFullReportNpsModel vasFullReportNpsModel) {
        String numOrder = vasFullReportNpsModel.getNumOrder();


        VasFullReportNpsModel saveReportKia = vasFullReportNpsRepository.save(vasFullReportNpsModel);
        LOGGER.log(Level.INFO, "Закрыт NPS по расширенному регламенту опроса клиента KIA: " + numOrder);
        System.out.println("Закрыт NPS по расширенному регламенту опроса клиента KIA: " + numOrder);
        return VasFullReportNpsRepository.saveReportKia(saveReportKia);
    }
}
