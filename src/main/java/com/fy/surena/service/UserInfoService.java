package com.fy.surena.service;

import com.fy.surena.model.UserInfo;

import java.util.List;

public interface UserInfoService {

    UserInfo save(UserInfo userInfo);

    void deleteUserInfoById(Long id);

    void deleteUserInfoByUsername(String username);

    int EditUserInfo(String firstname, String lastname,String modify_date, Long id);

    UserInfo getUserInfoById(Long id);

    UserInfo getUserInfoByUserName(String username);

    List<UserInfo> getUsersInfo();

    void changePassword(String newPass, String oldPass);

    boolean isExistUsername(String username);

    boolean isExistId (Long id);

}
