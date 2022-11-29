package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasUserModel;
import com.home.MyWorkTime.entity.VasUserModelDTO;
import org.springframework.stereotype.Component;

@Component
public class VasUserConverter {

    public VasUserModel fromVasUserModelDTOToVasUsersModel(VasUserModelDTO vasUserModelDTO){
        VasUserModel vasUserModel = new VasUserModel();
        vasUserModel.setId(vasUserModelDTO.getId());
        vasUserModel.setUserName(vasUserModelDTO.getUser_name());
        vasUserModel.setUserSurname(vasUserModelDTO.getUser_surname());
        vasUserModel.setUserPatronymic(vasUserModelDTO.getUser_patronymic());
        vasUserModel.setUserLogin(vasUserModelDTO.getUser_login());
        vasUserModel.setUserPassword(vasUserModelDTO.getUser_password());
        vasUserModel.setUserMail(vasUserModelDTO.getUser_mail());
        return vasUserModel;
    }

    public VasUserModelDTO fromVasUserModelToVasUserModelDTO (VasUserModel vasUserModel){

        return VasUserModelDTO.builder()
                .id(vasUserModel.getId())
                .user_name(vasUserModel.getUserName())
                .user_surname(vasUserModel.getUserSurname())
                .user_patronymic(vasUserModel.getUserPatronymic())
                .user_login(vasUserModel.getUserLogin())
                .user_password("***********") //vasUserModel.getUser_password()
                .user_mail(vasUserModel.getUserMail())
                .build();
    }
}
