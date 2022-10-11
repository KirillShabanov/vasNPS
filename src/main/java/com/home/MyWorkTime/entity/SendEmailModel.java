package com.home.MyWorkTime.entity;

import lombok.Data;

import java.util.Map;


@Data
public class SendEmailModel {

    String from;
    String to;
    String subject;
    String text;
    String template;
    Map<String, Object> properties;
}
