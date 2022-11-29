package com.home.MyWorkTime.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
