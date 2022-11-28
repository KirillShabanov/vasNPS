package com.home.MyWorkTime.controller;

import com.home.MyWorkTime.entity.VasNpsModel;
import com.home.MyWorkTime.entity.VasNpsModelDTO;
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
    public VasNpsModel saveOrder(@RequestBody VasNpsModelDTO vasNpsModelDTO) {
        String[] notBase = new String[]{"Послепродажная установка доп.оборудования",
                "Внутр. работы", "Предпр. внутр. Б/У",
                "Предп. внутр.", "Предп. ВНЕШН.."};
        String checkedCategory = vasNpsModelDTO.getCategory();
        for (String s : notBase) {
            if (checkedCategory.equals(s)) {
                return null;
            }
        }
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
