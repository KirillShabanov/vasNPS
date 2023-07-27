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

import com.home.MyWorkTime.entity.SendEmailModel;
import com.home.MyWorkTime.service.SendEmailService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.mail.MessagingException;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin
@RestController
public class SendEmailController {

    final
    SendEmailService sendEmailService;

    public SendEmailController(SendEmailService sendEmailService) {
        this.sendEmailService = sendEmailService;
    }

    @RequestMapping("/text")
    public String sendText(Model model){
        SendEmailModel sendEmailModel = new SendEmailModel();
        sendEmailModel.setFrom("info@vitautocity.by");
        sendEmailModel.setTo("k.shabanov@vitautocity.by");
        sendEmailModel.setSubject("Subject");
        sendEmailModel.setText("Text");
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "Kirill");
        properties.put("subscriptionDate", LocalDate.now().toString());
        sendEmailModel.setProperties(properties);
        sendEmailService.sendTextMessage(sendEmailModel);
        return "success.html";
    }

    @RequestMapping("/newCar{carVin}&{carDateArrival}")
    public String sendHtml(@PathVariable String carVin,
                           @PathVariable String carDateArrival,
                           Model model) throws MessagingException {
        SendEmailModel sendEmailModel = new SendEmailModel();
        sendEmailModel.setFrom("info@vitautocity.by");
        sendEmailModel.setTo("k.shabanov@vitautocity.by");
        sendEmailModel.setSubject("Поступление нового автомобиля на склад");
        sendEmailModel.setTemplate("newCar.html");
        Map<String, Object> properties = new HashMap<>();
        properties.put("vin", carVin);
        properties.put("date_arrival", carDateArrival);
        properties.put("subscriptionDate", LocalDate.now().toString());
        sendEmailModel.setProperties(properties);
        sendEmailService.sendHtmlMessage(sendEmailModel);
        return "success.html";
    }


    public String sendHtmlKiaFirstCall(FileInputStream filenamePost,
                                       Model model) throws MessagingException {
        SendEmailModel sendEmailModel = new SendEmailModel();
        sendEmailModel.setFrom("info@vitautocity.by");
        sendEmailModel.setTo("k.shabanov@vitautocity.by");
        sendEmailModel.setSubject("Поступление нового автомобиля на склад");
        sendEmailModel.setTemplate("newCar.html");
        Map<String, Object> properties = new HashMap<>();
        properties.put("subscriptionDate", LocalDate.now().toString());
        sendEmailModel.setProperties(properties);
        sendEmailService.sendHtmlMessage(sendEmailModel);
        return "success.html";
    }
}
