package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasCheckListEngineerKiaModel;
import com.home.MyWorkTime.repository.VasCheckListEngineerKiaRepository;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
public class VasCheckListEngineerKiaService {

    private VasCheckListEngineerKiaRepository vasCheckListEngineerKiaRepository;

    private static final Logger LOGGER = Logger.getLogger(VasCheckListEngineerKiaService.class.getName());

    @Autowired
    public VasCheckListEngineerKiaService (VasCheckListEngineerKiaRepository vasCheckListEngineerKiaRepository){
        this.vasCheckListEngineerKiaRepository = vasCheckListEngineerKiaRepository;
    }

    public List<VasCheckListEngineerKiaModel> findAllCheckListNotCancel(){
        LOGGER.log(Level.INFO, "Отображён не полный чек-лист ИТ KIA: ");
        return vasCheckListEngineerKiaRepository.findAllCheckListNotCancel();
    }

    public List<VasCheckListEngineerKiaModel> findAllCheckListNotCancelFromNum(String numOrderCheckKia) {
        LOGGER.log(Level.INFO, "Запрос не полный чек-лист ИТ KIA: " + numOrderCheckKia);
        return vasCheckListEngineerKiaRepository.findAllCheckListNotCancelFromNum(numOrderCheckKia);
    }

    public VasCheckListEngineerKiaModel saveCheckListEngineerKia(@NotNull VasCheckListEngineerKiaModel vasCheckListEngineerKiaModel){
        VasCheckListEngineerKiaModel saveCheckListEngineerKia = vasCheckListEngineerKiaRepository.save(vasCheckListEngineerKiaModel);
        LOGGER.log(Level.INFO, "Сохранён чек-лист ИТ KIA: ");
        System.out.println(saveCheckListEngineerKia);
        return VasCheckListEngineerKiaRepository.saveCheckListEngineerKia(saveCheckListEngineerKia);

    }
}
