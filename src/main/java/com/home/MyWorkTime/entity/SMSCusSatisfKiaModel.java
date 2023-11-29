package com.home.MyWorkTime.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SMSCusSatisfKiaModel {
    
    private Long phone_number;
    private String extra_id;
    private String callback_url;
    private LocalDateTime start_time;
    private String tag;
    private String channels;
    private String channel_options;
    private String sms;
    private String text;
    private String alpha_name;
    private String ttl;
    
}
