package com.home.MyWorkTime.controller;

import com.home.MyWorkTime.exception.UserValidator;
import com.home.MyWorkTime.service.SecurityService;
import com.home.MyWorkTime.service.VasUserService;
import lombok.extern.java.Log;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
@CrossOrigin
public class VasMyWorktimeController {

    private final VasUserService vasUserService;
    private final SecurityService securityService;
    private final UserValidator userValidator;

    public VasMyWorktimeController(VasUserService vasUserService, SecurityService securityService, UserValidator userValidator) {
        this.vasUserService = vasUserService;
        this.securityService = securityService;
        this.userValidator = userValidator;

    }


    @GetMapping("/authorizationVas")
    public String login(Model model, String error, String logout){
        if (error != null){
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null){
            model.addAttribute("message", "Logged out successfully");
        }

        return "login";
    }

    @GetMapping(value = {"/", "/myWorktime"})
    public String welcome(Model model){
        return "Welcome";
    }
}
