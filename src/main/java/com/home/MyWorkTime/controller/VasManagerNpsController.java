package com.home.MyWorkTime.controller;

import com.home.MyWorkTime.entity.VasManagerNpsModel;
import com.home.MyWorkTime.service.VasManagerNpsService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vas_manager")
@Log
@CrossOrigin
public class VasManagerNpsController {

    private final VasManagerNpsService vasManagerNpsService;

    public VasManagerNpsController(VasManagerNpsService vasManagerNpsService) {
        this.vasManagerNpsService = vasManagerNpsService;
    }

    @PostMapping("/saveNpsCall")
    public VasManagerNpsModel saveNpsCall(@RequestBody VasManagerNpsModel vasManagerNpsModel){
        return vasManagerNpsService.saveNpsCall(vasManagerNpsModel);
    }

    @GetMapping("/findAllNpsTo")
    public List<VasManagerNpsModel> findAllNpsTo(){
        return vasManagerNpsService.findAllNpsTo();
    }

    @GetMapping("/findAllNpsCc")
    public List<VasManagerNpsModel> findAllNpsCc(){
        return vasManagerNpsService.findAllNpsCc();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNpsTo(@PathVariable Long id){
        vasManagerNpsService.deleteNpsTo(id);
        return vasManagerNpsService.deleteNpsTo(id);
    }

    @GetMapping("/findAllTechnical")
    public List<VasManagerNpsModel> findAllTechnical(){
        return vasManagerNpsService.findAllTechnical();
    }

    @GetMapping("/findAllBodyRepair")
    public List<VasManagerNpsModel> findAllBodyRepair(){
        return vasManagerNpsService.findAllBodyRepair();
    }

    @PostMapping("/saveManager")
    public VasManagerNpsModel saveManager(@RequestBody VasManagerNpsModel vasManagerNpsModel){
        return vasManagerNpsService.saveNpsCall(vasManagerNpsModel);
    }
}
