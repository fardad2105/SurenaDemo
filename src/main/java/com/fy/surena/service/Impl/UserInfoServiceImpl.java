package com.fy.surena.service.Impl;

import com.fy.surena.model.UserInfo;
import com.fy.surena.repository.UserInfoRepository;
import com.fy.surena.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;



    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public void deleteUserInfoById(Long id) {
         userInfoRepository.deleteById(id);
    }

    @Override
    public void deleteUserInfoByUsername(String username) {
         userInfoRepository.deleteUserInfoByUserName(username);
    }

    @Override
    public int EditUserInfo(String firstname, String lastname,String modify_date, Long id) {
        return userInfoRepository.updateUserInfo(firstname,lastname,modify_date, id);
    }

    @Override
    public UserInfo getUserInfoById(Long id) {
        return userInfoRepository.findById(id).get();

    }

    @Override
    public UserInfo getUserInfoByUserName(String username) {
        return userInfoRepository.getUserInfoByUsername(username);
    }

    @Override
    public List<UserInfo> getUsersInfo() {
        return userInfoRepository.findAll();
    }

    @Override
    public void changePassword(String newPass, String oldPass) {
         userInfoRepository.changePassword(newPass,oldPass);
    }

    @Override
    public boolean isExistUsername(String username) {
        return userInfoRepository.isUserExistWithUsername(username);
    }

    @Override
    public boolean isExistId(Long id) {
        return userInfoRepository.existsById(id);
    }


}
