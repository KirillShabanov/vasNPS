package com.home.MyWorkTime.controller;


import com.home.MyWorkTime.entity.VasUserModelDTO;
import com.home.MyWorkTime.exception.UserValidator;
import com.home.MyWorkTime.service.SecurityService;
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
    private final SecurityService securityService;
    private final UserValidator userValidator;

    public VasUserController(VasUserService vasUserService, VasUserConverter vasUserConverter, SecurityService securityService, UserValidator userValidator) {
        this.vasUserService = vasUserService;
        this.vasUserConverter = vasUserConverter;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @GetMapping("/findAll")
    public List<VasUserModelDTO> findAllUsers(){
        log.info("Handling find all users request");
        return vasUserService.findAll();
    }
}
