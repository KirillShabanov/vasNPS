package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasCheckListMechanicalKiaModel;
import com.home.MyWorkTime.repository.VasCheckListMechanicalKiaRepository;
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
public class VasCheckListMechanicalKiaService {

    private VasCheckListMechanicalKiaRepository vasCheckListMechanicalKiaRepository;

    private static final Logger LOGGER = Logger.getLogger(VasCheckListMechanicalKiaService.class.getName());

    @Autowired
    public VasCheckListMechanicalKiaService (VasCheckListMechanicalKiaRepository vasCheckListMechanicalKiaRepository){
        this.vasCheckListMechanicalKiaRepository = vasCheckListMechanicalKiaRepository;
    }

    public List<VasCheckListMechanicalKiaModel> findAllCheckListNotCancelMechanical(){
        LOGGER.log(Level.INFO, "Отображён не полный чек-лист механика KIA: ");
        return vasCheckListMechanicalKiaRepository.findAllCheckListNotCancelMechanical();
    }

    public List<VasCheckListMechanicalKiaModel> findAllCheckListNotCancelFromNumMechanical(String numOrderCheckKia) {
        LOGGER.log(Level.INFO, "Запрос не полный чек-лист механика KIA: " + numOrderCheckKia);
        return vasCheckListMechanicalKiaRepository.findAllCheckListNotCancelFromNumMechanical(numOrderCheckKia);
    }

    public VasCheckListMechanicalKiaModel saveCheckListMechanicalKia(@NotNull VasCheckListMechanicalKiaModel vasCheckListMechanicalKiaModel){
        VasCheckListMechanicalKiaModel saveCheckListMechanicalKia = vasCheckListMechanicalKiaRepository.save(vasCheckListMechanicalKiaModel);
        LOGGER.log(Level.INFO, "Сохранён чек-лист механика KIA: ");
        return VasCheckListMechanicalKiaRepository.saveCheckListMechanicalKia(saveCheckListMechanicalKia);

    }
}
