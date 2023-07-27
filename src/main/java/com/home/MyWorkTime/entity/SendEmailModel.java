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
