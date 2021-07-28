package com.fy.surena.service;

import com.fy.surena.mapstruct.dtos.ChangePassDto;
import com.fy.surena.model.UserInfo;

import java.util.List;

public interface UserInfoService {

    UserInfo save(UserInfo userInfo);

    void deleteUserInfoById(Long id);

    void deleteUserInfoByUsername(String username);

    int EditUserInfo(String firstname, String lastname, Long id);

    UserInfo getUserInfoById(Long id);

    UserInfo getUserInfoByUserName(String username);

    List<UserInfo> getUsersInfo();

    void changePassword(ChangePassDto changePassDto);

}
