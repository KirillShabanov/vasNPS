package com.home.MyWorkTime.controller;

import com.home.MyWorkTime.entity.VasFullReportNpsModel;
import com.home.MyWorkTime.entity.VasNpsModel;
import com.home.MyWorkTime.service.VasFullReportNpsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vasFullReportNps")
@CrossOrigin
public class VasFullReportNpsController {

    private final VasFullReportNpsService vasFullReportNpsService;

    public VasFullReportNpsController(VasFullReportNpsService vasFullReportNpsService) {
        this.vasFullReportNpsService = vasFullReportNpsService;
    }

    @GetMapping("/numOrder/{numOrder}")
    public List<VasNpsModel> searchReport(@PathVariable String numOrder){
        return vasFullReportNpsService.searchReport(numOrder);
    }

    @GetMapping("/countReport")
    public long countReport(){
        return vasFullReportNpsService.countReport();
    }

    @PostMapping("/saveReportKia")
    public VasFullReportNpsModel saveReportKia(@RequestBody VasFullReportNpsModel vasFullReportNpsModel) {
        return vasFullReportNpsService.saveReportKia(vasFullReportNpsModel);
    }
}
