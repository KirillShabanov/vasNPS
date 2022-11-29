package com.home.MyWorkTime.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name="vas_users")
public class VasUserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_surname")
    private String userSurname;
    @Column(name = "user_patronymic")
    private String userPatronymic;
    @Column(name = "user_login")
    private String userLogin;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_mail")
    private String userMail;

    @Transient
    transient private String confirmPassword;

    @ManyToMany
            @JoinTable(name = "vas_users_roles", joinColumns = @JoinColumn(name = "vas_users_id"),
    inverseJoinColumns = @JoinColumn(name = "vas_roles_id"))
    @JsonManagedReference
    private Set<VasRoles> vasRoles;

    public VasUserModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserPatronymic() {
        return userPatronymic;
    }

    public void setUserPatronymic(String userPatronymic) {
        this.userPatronymic = userPatronymic;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<VasRoles> getVasRoles() {
        return vasRoles;
    }

    public void setVasRoles(Set<VasRoles> vasRoles) {
        this.vasRoles = vasRoles;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "user_name = " + userName + ", " +
                "user_surname = " + userSurname + ", " +
                "user_patronymic = " + userPatronymic + ", " +
                "user_login = " + userLogin + ", " +
                "user_password = " + userPassword + ", " +
                "user_mail = " + userMail + ", " +
                "confirmPassword = " + confirmPassword + ")";
    }
}
