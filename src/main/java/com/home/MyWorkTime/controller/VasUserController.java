package com.home.MyWorkTime.controller;


import com.home.MyWorkTime.entity.VasUserModelDTO;
import com.home.MyWorkTime.service.VasUserConverter;
import com.home.MyWorkTime.service.VasUserService;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vas_users")
@Log
@CrossOrigin
public class VasUserController {

    private final VasUserService vasUserService;
    private final VasUserConverter vasUserConverter;


    public VasUserController(VasUserService vasUserService, VasUserConverter vasUserConverter) {
        this.vasUserService = vasUserService;
        this.vasUserConverter = vasUserConverter;

    }

    @GetMapping("/findAll")
    public List<VasUserModelDTO> findAllUsers(){
        log.info("Handling find all users request");
        return vasUserService.findAll();
    }
}
