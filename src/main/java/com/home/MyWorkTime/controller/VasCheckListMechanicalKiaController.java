package com.home.MyWorkTime.controller;

import com.home.MyWorkTime.entity.VasCheckListMechanicalKiaModel;
import com.home.MyWorkTime.service.VasCheckListMechanicalKiaService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/vas_check_list_mechanical_kia")
@Log
@CrossOrigin
@AllArgsConstructor
public class VasCheckListMechanicalKiaController {

    private final VasCheckListMechanicalKiaService vasCheckListMechanicalKiaService;

    @PostMapping("/saveCheckListMechanicalKia")
    public VasCheckListMechanicalKiaModel saveCheckListMechanicalKia(@RequestBody VasCheckListMechanicalKiaModel vasCheckListMechanicalKiaModel){
        return vasCheckListMechanicalKiaService.saveCheckListMechanicalKia(vasCheckListMechanicalKiaModel);
    }

    @GetMapping("/findAllCheckListNotCancelMechanical")
    public List<VasCheckListMechanicalKiaModel> findAllCheckListNotCancelMechanical(){
        return vasCheckListMechanicalKiaService.findAllCheckListNotCancelMechanical();
    }

    @GetMapping("/findAllCheckListNotCancelFromNumMechanical/{numOrderCheckKia}")
    public List<VasCheckListMechanicalKiaModel> findAllCheckListNotCancelFromNumMechanical(@PathVariable String numOrderCheckKia){
        return vasCheckListMechanicalKiaService.findAllCheckListNotCancelFromNumMechanical(numOrderCheckKia);
    }

    @PutMapping("/updateCheckListMechanicalKia/{numOrderCheckKia}")
    Stream<VasCheckListMechanicalKiaModel> updateCheckListMechanicalKia(@RequestBody VasCheckListMechanicalKiaModel newVasCheckListMechanicalKiaModel,
                                                                    @PathVariable String numOrderCheckKia){
        return vasCheckListMechanicalKiaService
                .findAllCheckListNotCancelFromNumMechanical(numOrderCheckKia).stream().map(vasCheckListMechanicalKiaModel -> {

                    vasCheckListMechanicalKiaModel.setSurnameMechanicalKia(newVasCheckListMechanicalKiaModel.getSurnameMechanicalKia());
                    vasCheckListMechanicalKiaModel.setMechanicalKiaVin(newVasCheckListMechanicalKiaModel.getMechanicalKiaVin());
                    vasCheckListMechanicalKiaModel.setInWorkRepair(newVasCheckListMechanicalKiaModel.getInWorkRepair());
                    vasCheckListMechanicalKiaModel.setDateSaveRepairInspection(newVasCheckListMechanicalKiaModel.getDateSaveRepairInspection());

                    vasCheckListMechanicalKiaModel.setRepairQualityControl1(newVasCheckListMechanicalKiaModel.getRepairQualityControl1());
                    vasCheckListMechanicalKiaModel.setRepairQualityControl2(newVasCheckListMechanicalKiaModel.getRepairQualityControl2());
                    vasCheckListMechanicalKiaModel.setRepairQualityControl3(newVasCheckListMechanicalKiaModel.getRepairQualityControl3());
                    vasCheckListMechanicalKiaModel.setRepairQualityControl4(newVasCheckListMechanicalKiaModel.getRepairQualityControl4());
                    vasCheckListMechanicalKiaModel.setRepairQualityControl5(newVasCheckListMechanicalKiaModel.getRepairQualityControl5());
                    vasCheckListMechanicalKiaModel.setRepairQualityControl6(newVasCheckListMechanicalKiaModel.getRepairQualityControl6());
                    vasCheckListMechanicalKiaModel.setRepairQualityControl7(newVasCheckListMechanicalKiaModel.getRepairQualityControl7());
                    vasCheckListMechanicalKiaModel.setRepairQualityControl8(newVasCheckListMechanicalKiaModel.getRepairQualityControl8());
                    vasCheckListMechanicalKiaModel.setRepairQualityControl9(newVasCheckListMechanicalKiaModel.getRepairQualityControl9());
                    vasCheckListMechanicalKiaModel.setRepairQualityControl10(newVasCheckListMechanicalKiaModel.getRepairQualityControl10());

                    vasCheckListMechanicalKiaModel.setParkingLotCheck1(newVasCheckListMechanicalKiaModel.getParkingLotCheck1());
                    vasCheckListMechanicalKiaModel.setParkingLotCheck2(newVasCheckListMechanicalKiaModel.getParkingLotCheck2());
                    vasCheckListMechanicalKiaModel.setParkingLotCheck3(newVasCheckListMechanicalKiaModel.getParkingLotCheck3());
                    vasCheckListMechanicalKiaModel.setParkingLotCheck4(newVasCheckListMechanicalKiaModel.getParkingLotCheck4());
                    vasCheckListMechanicalKiaModel.setParkingLotCheck5(newVasCheckListMechanicalKiaModel.getParkingLotCheck5());
                    vasCheckListMechanicalKiaModel.setParkingLotCheck6(newVasCheckListMechanicalKiaModel.getParkingLotCheck6());
                    vasCheckListMechanicalKiaModel.setParkingLotCheck7(newVasCheckListMechanicalKiaModel.getParkingLotCheck7());
                    vasCheckListMechanicalKiaModel.setParkingLotCheck8(newVasCheckListMechanicalKiaModel.getParkingLotCheck8());
                    vasCheckListMechanicalKiaModel.setParkingLotCheck9(newVasCheckListMechanicalKiaModel.getParkingLotCheck9());
                    vasCheckListMechanicalKiaModel.setParkingLotCheck10(newVasCheckListMechanicalKiaModel.getParkingLotCheck10());
                    vasCheckListMechanicalKiaModel.setParkingLotCheck11(newVasCheckListMechanicalKiaModel.getParkingLotCheck11());

                    vasCheckListMechanicalKiaModel.setCheckInMotion1(newVasCheckListMechanicalKiaModel.getCheckInMotion1());
                    vasCheckListMechanicalKiaModel.setCheckInMotion2(newVasCheckListMechanicalKiaModel.getCheckInMotion2());
                    vasCheckListMechanicalKiaModel.setCheckInMotion3(newVasCheckListMechanicalKiaModel.getCheckInMotion3());
                    vasCheckListMechanicalKiaModel.setCheckInMotion4(newVasCheckListMechanicalKiaModel.getCheckInMotion4());

                    vasCheckListMechanicalKiaModel.setQualityControl1(newVasCheckListMechanicalKiaModel.getQualityControl1());
                    vasCheckListMechanicalKiaModel.setNotesMaster(newVasCheckListMechanicalKiaModel.getNotesMaster());
                    vasCheckListMechanicalKiaModel.setNotesWork(newVasCheckListMechanicalKiaModel.getNotesWork());
                    vasCheckListMechanicalKiaModel.setExplanationsWork(newVasCheckListMechanicalKiaModel.getExplanationsWork());
                    vasCheckListMechanicalKiaModel.setQualityControl5(newVasCheckListMechanicalKiaModel.getQualityControl5());

                    vasCheckListMechanicalKiaModel = vasCheckListMechanicalKiaService.saveCheckListMechanicalKia(vasCheckListMechanicalKiaModel);
                    return vasCheckListMechanicalKiaModel;
                });
    }
}
