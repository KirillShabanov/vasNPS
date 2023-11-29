package com.home.MyWorkTime.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.home.MyWorkTime.config.SMSConfig;
import com.home.MyWorkTime.entity.CustomerSatisfactionReportKiaModel;
import com.home.MyWorkTime.entity.SMSCusSatisfKiaModel;
import com.home.MyWorkTime.repository.CustomerSatisfactionReportKiaRepository;

@Component
public class SMSCusSatisfKiaService {

    private static final Logger LOGGER = Logger.getLogger(SMSCusSatisfKiaService.class.getName());

    private static SMSConfig smsConfig;
    private static CustomerSatisfactionReportKiaRepository customerSatisfactionReportKiaRepository;

    public SMSCusSatisfKiaService (SMSConfig smsConfig, 
                                    CustomerSatisfactionReportKiaRepository customerSatisfactionReportKiaRepository){
        SMSCusSatisfKiaService.smsConfig = smsConfig;
        SMSCusSatisfKiaService.customerSatisfactionReportKiaRepository = customerSatisfactionReportKiaRepository;
    }

    public static String getSMSClientId(){
        return smsConfig.getSmsClientId();
    }

    public static String getSMSExtraId(){
        return smsConfig.getSmsExtraId();
    }

    public static String getSMSCallbackUrl(){
        return smsConfig.getSmsCallbackUrl();
    }

    public static String getSMSTag(){
        return smsConfig.getSmsTag();
    }

    public static String getSMSTTL(){
        return smsConfig.getSmsTtl();
    }

    public static String getSMSTimeSendler(){
        return smsConfig.getSmsTimeSendler();
    }

    @Scheduled(cron = "${sms.CustSatisfRepSMS.cron}")
    void smsCusSatisfRepKiaSendler() throws IOException {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateForSMS = simpleDateFormat.format(new Date());

        List<CustomerSatisfactionReportKiaModel> modelForSms = customerSatisfactionReportKiaRepository.getModelForSms(dateForSMS);

        if(!(modelForSms.isEmpty())){
            for (int i=0; i<modelForSms.size(); i++){
                String phone = modelForSms.get(i).getPhone();
                String numOrder = modelForSms.get(i).getNumOrder();

                smsCusSatisfRepKiaGenerettedSMS(phone, numOrder, dateForSMS);
                LOGGER.log(Level.INFO, "SMS по опросу отправлены.");
            }
        }
    }

    private static SMSCusSatisfKiaModel smsCusSatisfRepKiaGenerettedSMS (String phone, String numOrder, String dateForSMS) throws IOException{

        URL url = new URL("https://api.communicator.mts.by/" + getSMSClientId() + "/json2/simple");
        String dateSMSSend = dateForSMS + " " + getSMSTimeSendler(); //Настройка даты и времени отправки SMS от провайдера.
        String urlForText = "https://report.vitautocity.by/?numberOrder=" + numOrder;

        String TextSMS = "Вместе сделаем сервис ВитебскАвтоСити лучше! " +
        "Пройдите короткий опрос. " +
        "Это поможет нам повысить качество и сделать сервис еще удобнее: " +
        urlForText + ". Телефон для связи: +375297777126";

        String authorization = "Basic dml0YXV0b2NpdHlfc3NFYjpVUE9FbG4="; //Подстановка для авторизации

        String[] channels = new String[1];
        channels[0] = "sms";

        JSONObject sms = new JSONObject();
        sms.put("text", TextSMS);
        sms.put("alpha_name", "");
        sms.put("ttl", getSMSTTL());

        JSONObject channelOptions = new JSONObject();
        channelOptions.put("sms", sms);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone_number", phone);
        jsonObject.put("extra_id", getSMSExtraId());
        jsonObject.put("callback_url", getSMSCallbackUrl());
        jsonObject.put("start_time", dateSMSSend);
        jsonObject.put("tag", getSMSTag());
        jsonObject.put("channels", channels);
        jsonObject.put("channel_options", channelOptions);
        
       
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", authorization);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        
        try(OutputStream os = conn.getOutputStream()) {

            byte[] input = jsonObject.toString().getBytes("UTF-8");
            os.write(input, 0, input.length);	

        }

        try(BufferedReader br = new BufferedReader(

            new InputStreamReader(conn.getInputStream(), "UTF-8"))) {

                StringBuilder response = new StringBuilder();
                String responseLine = null;

                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim().toString());
                }

            String messageData = response.toString();

            customerSatisfactionReportKiaRepository.addIdSMS(numOrder, messageData);  
        }
        return null;
    }
}
