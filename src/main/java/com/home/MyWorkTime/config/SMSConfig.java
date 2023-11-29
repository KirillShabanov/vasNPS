package com.home.MyWorkTime.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
@PropertySource("application.properties")
public class SMSConfig {

    @Value("${sms.clientId}")
    String smsClientId;
    @Value("${sms.extraId}")
    String smsExtraId;
    @Value("${sms.callbackUrl}")
    String smsCallbackUrl;
    @Value("${sms.tag}")
    String smsTag;
    @Value("${sms.ttl}")
    String smsTtl;
    @Value("${sms.time.sendler}")
    String smsTimeSendler;
}
