package com.home.MyWorkTime.controller;

import com.home.MyWorkTime.entity.VasNpsModel;
import com.home.MyWorkTime.service.VasNpsService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/vas_nps")
@AllArgsConstructor
@Log
@CrossOrigin
public class VasNpsController {

    private final VasNpsService vasNpsService;

    @PostMapping("/saveOrder")
    public VasNpsModel saveOrder(@RequestBody VasNpsModel vasNpsModel){
        return vasNpsService.saveOrder(vasNpsModel);
    }

    @GetMapping("/{numOrder}&{callDate}")
    void updateCallDate(@PathVariable int numOrder,
                        @PathVariable String callDate){
        vasNpsService.updateCallDate(numOrder, callDate);
    }

    @GetMapping("/{numOrder}/{nps}/{adminName}/{adminComment}/{callStatus}")
    void npsCall(@PathVariable int numOrder,
                 @PathVariable int nps,
                 @PathVariable String adminName,
                 @PathVariable String adminComment,
                 @PathVariable String callStatus){
        vasNpsService.npsCall(numOrder, nps, adminComment, adminName, callStatus);
    }
}
