/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  
 * 
 *  Date of remmark: 27/07/2023
 */
package com.home.MyWorkTime.controller;

import com.home.MyWorkTime.entity.VasNpsModel;
import com.home.MyWorkTime.entity.VasNpsModelDTO;
import com.home.MyWorkTime.service.VasNpsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vas_nps")
@CrossOrigin
public class VasNpsController {

    private final VasNpsService vasNpsService;

    public VasNpsController(VasNpsService vasNpsService) {
        this.vasNpsService = vasNpsService;
    }

    @PostMapping("/saveOrder")
    public VasNpsModel saveOrder(@RequestBody VasNpsModelDTO vasNpsModelDTO) {
        return vasNpsService.saveOrder(vasNpsModelDTO);
    }

    @PostMapping("/updateCallDate")
    public VasNpsModel updateCallDate(@RequestBody VasNpsModelDTO vasNpsModelDTO) {
        return vasNpsService.updateCallDate(vasNpsModelDTO);
    }


    @PostMapping("/outgoingCall")
    public VasNpsModel outgoingCall(@RequestBody VasNpsModelDTO vasNpsModelDTO){
        return vasNpsService.outgoingCall(vasNpsModelDTO);
    }
}
