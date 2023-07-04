package com.home.MyWorkTime.controller;


import com.home.MyWorkTime.entity.VasCheckListEngineerKiaModel;
import com.home.MyWorkTime.service.VasCheckListEngineerKiaService;
import lombok.AllArgsConstructor;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;


@RestController
@RequestMapping("/vas_check_list_engineer_kia")
@CrossOrigin
@AllArgsConstructor
public class VasCheckListEngineerKiaController {

    private final VasCheckListEngineerKiaService vasCheckListEngineerKiaService;

    @PostMapping("/saveCheckListEngineerKia")
    public VasCheckListEngineerKiaModel saveCheckListEngineerKia(@RequestBody VasCheckListEngineerKiaModel vasCheckListEngineerKiaModel){
        return vasCheckListEngineerKiaService.saveCheckListEngineerKia(vasCheckListEngineerKiaModel);
    }

    @GetMapping("/findAllCheckListNotCancel")
    public List<VasCheckListEngineerKiaModel> findAllCheckListNotCancel(){
        return vasCheckListEngineerKiaService.findAllCheckListNotCancel();
    }

    @GetMapping("/findAllCheckListNotCancelFromNum/{numOrderCheckKia}")
    public List<VasCheckListEngineerKiaModel> findAllCheckListNotCancelFromNum(@PathVariable String numOrderCheckKia){
        return vasCheckListEngineerKiaService.findAllCheckListNotCancelFromNum(numOrderCheckKia);
    }

    @PutMapping("updateCheckListEngineerKia/{numOrderCheckKia}")
    Stream<VasCheckListEngineerKiaModel> updateCheckListEngineerKia(@RequestBody VasCheckListEngineerKiaModel newVasCheckListEngineerKiaModel,
                                                                      @PathVariable String numOrderCheckKia){
        return vasCheckListEngineerKiaService
                .findAllCheckListNotCancelFromNum(numOrderCheckKia).stream().map(vasCheckListEngineerKiaModel -> {

                    vasCheckListEngineerKiaModel.setSurnameEngineerKia(newVasCheckListEngineerKiaModel.getSurnameEngineerKia());
                    vasCheckListEngineerKiaModel.setDateSaveInspection(newVasCheckListEngineerKiaModel.getDateSaveInspection());
                    vasCheckListEngineerKiaModel.setInWork(newVasCheckListEngineerKiaModel.getInWork());
                    vasCheckListEngineerKiaModel.setReceptionQuestion1(newVasCheckListEngineerKiaModel.getReceptionQuestion1());
                    vasCheckListEngineerKiaModel.setReceptionQuestion2(newVasCheckListEngineerKiaModel.getReceptionQuestion2());
                    vasCheckListEngineerKiaModel.setReceptionQuestion3(newVasCheckListEngineerKiaModel.getReceptionQuestion3());
                    vasCheckListEngineerKiaModel.setReceptionQuestion4(newVasCheckListEngineerKiaModel.getReceptionQuestion4());
                    vasCheckListEngineerKiaModel.setReceptionQuestion5(newVasCheckListEngineerKiaModel.getReceptionQuestion5());
                    vasCheckListEngineerKiaModel.setReceptionQuestion6(newVasCheckListEngineerKiaModel.getReceptionQuestion6());
                    vasCheckListEngineerKiaModel.setReceptionQuestion7(newVasCheckListEngineerKiaModel.getReceptionQuestion7());
                    vasCheckListEngineerKiaModel.setReceptionQuestion8(newVasCheckListEngineerKiaModel.getReceptionQuestion8());
                    vasCheckListEngineerKiaModel.setReceptionQuestion9(newVasCheckListEngineerKiaModel.getReceptionQuestion9());
                    vasCheckListEngineerKiaModel.setReceptionQuestion10(newVasCheckListEngineerKiaModel.getReceptionQuestion10());
                    vasCheckListEngineerKiaModel.setReceptionQuestion11(newVasCheckListEngineerKiaModel.getReceptionQuestion11());
                    vasCheckListEngineerKiaModel.setReceptionQuestion12(newVasCheckListEngineerKiaModel.getReceptionQuestion12());
                    vasCheckListEngineerKiaModel.setReceptionQuestion13(newVasCheckListEngineerKiaModel.getReceptionQuestion13());
                    vasCheckListEngineerKiaModel.setReceptionQuestion14(newVasCheckListEngineerKiaModel.getReceptionQuestion14());
                    vasCheckListEngineerKiaModel.setReceptionQuestion15(newVasCheckListEngineerKiaModel.getReceptionQuestion15());
                    vasCheckListEngineerKiaModel.setReceptionQuestion16(newVasCheckListEngineerKiaModel.getReceptionQuestion16());

                    vasCheckListEngineerKiaModel.setInspectionQuestion1(newVasCheckListEngineerKiaModel.getInspectionQuestion1());
                    vasCheckListEngineerKiaModel.setInspectionQuestion2(newVasCheckListEngineerKiaModel.getInspectionQuestion2());
                    vasCheckListEngineerKiaModel.setInspectionQuestion3(newVasCheckListEngineerKiaModel.getInspectionQuestion3());
                    vasCheckListEngineerKiaModel.setInspectionQuestion4(newVasCheckListEngineerKiaModel.getInspectionQuestion4());
                    vasCheckListEngineerKiaModel.setInspectionQuestion5(newVasCheckListEngineerKiaModel.getInspectionQuestion5());
                    vasCheckListEngineerKiaModel.setInspectionQuestion6(newVasCheckListEngineerKiaModel.getInspectionQuestion6());

                    vasCheckListEngineerKiaModel.setWaitingQuestion1(newVasCheckListEngineerKiaModel.getWaitingQuestion1());
                    vasCheckListEngineerKiaModel.setWaitingQuestion2(newVasCheckListEngineerKiaModel.getWaitingQuestion2());
                    vasCheckListEngineerKiaModel.setWaitingQuestion3(newVasCheckListEngineerKiaModel.getWaitingQuestion3());

                    vasCheckListEngineerKiaModel.setIssuingQuestion1(newVasCheckListEngineerKiaModel.getIssuingQuestion1());
                    vasCheckListEngineerKiaModel.setIssuingQuestion2(newVasCheckListEngineerKiaModel.getIssuingQuestion2());
                    vasCheckListEngineerKiaModel.setIssuingQuestion3(newVasCheckListEngineerKiaModel.getIssuingQuestion3());
                    vasCheckListEngineerKiaModel.setIssuingQuestion4(newVasCheckListEngineerKiaModel.getIssuingQuestion4());
                    vasCheckListEngineerKiaModel.setIssuingQuestion5(newVasCheckListEngineerKiaModel.getIssuingQuestion5());
                    vasCheckListEngineerKiaModel.setIssuingQuestion6(newVasCheckListEngineerKiaModel.getIssuingQuestion6());
                    vasCheckListEngineerKiaModel.setIssuingQuestion7(newVasCheckListEngineerKiaModel.getIssuingQuestion7());
                    vasCheckListEngineerKiaModel.setIssuingQuestion8(newVasCheckListEngineerKiaModel.getIssuingQuestion8());

                    vasCheckListEngineerKiaModel = vasCheckListEngineerKiaService.saveCheckListEngineerKia(vasCheckListEngineerKiaModel);
                    return vasCheckListEngineerKiaModel;
                });
    }

    @GetMapping("/countReportAnalyticsCancel")
    public long analyticsKiaEngineerCountReportCancel(){
        return vasCheckListEngineerKiaService.analyticsKiaEngineerCountReportCancel();
    }

    @GetMapping("/countReportAnalyticsNotCancel")
    public long analyticsKiaEngineerCountReportNotCancel(){
        return vasCheckListEngineerKiaService.analyticsKiaEngineerCountReportNotCancel();
    }

    @GetMapping("/analyticsKiaEngineerGeneralIndicator")
    public HashMap<Object, Object> analyticsKiaEngineerGeneralIndicator(){
        return vasCheckListEngineerKiaService.analyticsKiaEngineerGeneralIndicator();
    }

    @GetMapping("/findAllCheckListCancel")
    public List<VasCheckListEngineerKiaModel> findAllCheckListCancel(){
        return vasCheckListEngineerKiaService.findAllCheckListCancel();
    }

    @GetMapping("/findAllCheckListCancelFromNum/{numOrderCheckKia}")
    public List<VasCheckListEngineerKiaModel> findAllCheckListCancelFromNum(@PathVariable String numOrderCheckKia){
        return vasCheckListEngineerKiaService.findAllCheckListCancelFromNum(numOrderCheckKia);
    }

    @GetMapping("/countReportsKiaEngineerPeriod/{periodCheckListEngineerSurname}&{periodCheckListEngineerDateFrom}&{periodCheckListEngineerDateBy}")
    public List<VasCheckListEngineerKiaModel> countReportsKiaEngineerPeriod(@PathVariable String periodCheckListEngineerSurname, @PathVariable String periodCheckListEngineerDateFrom, @PathVariable String periodCheckListEngineerDateBy) throws ParseException{
        return vasCheckListEngineerKiaService.countReportsKiaEngineerPeriod(periodCheckListEngineerSurname, periodCheckListEngineerDateFrom, periodCheckListEngineerDateBy);                                                                       
    }

    @GetMapping("/downaloadReportsKiaEngineerPeriod/{periodCheckListEngineerSurname}&{periodCheckListEngineerDateFrom}&{periodCheckListEngineerDateBy}")
    public FileSystemResource downaloadReportsKiaEngineerPeriod(@PathVariable String periodCheckListEngineerSurname, @PathVariable String periodCheckListEngineerDateFrom, @PathVariable String periodCheckListEngineerDateBy) throws ParseException, IOException{
        return vasCheckListEngineerKiaService.createReportsKiaEngineerPeriod(periodCheckListEngineerSurname, periodCheckListEngineerDateFrom, periodCheckListEngineerDateBy);                                                                       
    }

}
