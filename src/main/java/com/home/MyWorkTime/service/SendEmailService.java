package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.SendEmailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;



@Service
@RequiredArgsConstructor
public class SendEmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    public void sendTextMessage(SendEmailModel sendEmailModel){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendEmailModel.getFrom());
        message.setTo(sendEmailModel.getTo());
        message.setSubject(sendEmailModel.getSubject());
        message.setText(sendEmailModel.getText());
        javaMailSender.send(message);
    }

    public void sendHtmlMessage(SendEmailModel sendEmailModel) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(sendEmailModel.getProperties());
        helper.setFrom(sendEmailModel.getFrom());
        helper.setTo(sendEmailModel.getTo());
        helper.setSubject(sendEmailModel.getSubject());
        String html = springTemplateEngine.process(sendEmailModel.getTemplate(),
                context);
        helper.setText(html, true);
        javaMailSender.send(message);
    }

}
