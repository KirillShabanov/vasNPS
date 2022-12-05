package com.home.MyWorkTime.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data
@Builder
@ToString
public class VasUserModelDTO {

    private Long id;
    private String user_name;
    private String user_surname;
    private String user_patronymic;
    private String user_login;
    private String user_password;
    private String user_mail;

}
