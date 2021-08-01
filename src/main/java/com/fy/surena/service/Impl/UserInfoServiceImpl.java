package com.fy.surena.service.Impl;

import com.fy.surena.config.ControllerUtils;
import com.fy.surena.config.MD5Util;
import com.fy.surena.exception.PasswordException;
import com.fy.surena.exception.UserExists;
import com.fy.surena.exception.UserIsNotDeleteException;
import com.fy.surena.exception.UserManagerException;
import com.fy.surena.mapstruct.dtos.ChangePassDto;
import com.fy.surena.mapstruct.dtos.UserInfoDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.UserInfo;
import com.fy.surena.repository.UserInfoRepository;
import com.fy.surena.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private MapStructMapper mapStructMapper;


    @Override
    public UserInfo save(UserInfo userInfo) {
        if (userInfoRepository.existsUserInfoByUsername(userInfo.getUsername())) {
            throw new UserExists("User with this username:" + userInfo.getUsername() + "is Exists");
        } else {
            SimpleDateFormat create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            userInfo.setCreateDate(create_date.format(now));

            // Encrypt Password
            userInfo.setPassword(MD5Util.string2MD5(userInfo.getPassword()));

        }
        return userInfoRepository.save(userInfo);
    }

    @Override
    public void deleteUserInfoById(Long id) {
        if (!userInfoRepository.existsById(id)) {
            throw new UserIsNotDeleteException("User with id: " + id + "is not exists");
        } else {
            userInfoRepository.deleteById(id);
        }
    }

    @Override
    public void deleteByUsername(String username) {
        if (!userInfoRepository.existsUserInfoByUsername(username)) {
            throw new UserIsNotDeleteException("User with id: " + username + "is not exists");
        } else {
            userInfoRepository.deleteByUsername(username);
        }
    }

    @Override
    public int EditUserInfo(String firstname, String lastname, Long id) {
        if (!userInfoRepository.existsById(id)) {
            throw new UserIsNotDeleteException("User with id: " + id + "is not exists");
        }
        SimpleDateFormat modify_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        return userInfoRepository.updateUserInfo(firstname, lastname, modify_date.format(now), id);
    }

    @Override
    public UserInfo getUserInfoById(Long id) {
        if (!userInfoRepository.existsById(id)) {
            throw new UserIsNotDeleteException("User with id: " + id + "is not exists");
        }
        return userInfoRepository.findById(id).get();
    }

    @Override
    public UserInfoDto findByUsername(String username) {
        UserInfoDto userInfoDto;
        if (!userInfoRepository.findByUsername(username).isPresent()) {
            throw new UserIsNotDeleteException("User with username: " + username + "is not exists");
        } else {
            userInfoDto = mapStructMapper.userInfoDtoGetByUserInfo(userInfoRepository.findByUsername(username).get());
        }
        return userInfoDto;
    }


    @Override
    public List<UserInfo> getUsersInfo() {
        return userInfoRepository.findAll();
    }

    @Override
    public void changePassword(ChangePassDto changePassDto) {
        String newPass = changePassDto.getNewPass();
        String oldPass = changePassDto.getOldPass();
        if (controllerUtils.isPasswordDifferent(newPass, oldPass)) {
            throw new PasswordException("Password do not match");
        }
        Optional<UserInfo> userInfo = userInfoRepository.findByUsername(changePassDto.getUsername());
        if (userInfo.isPresent()) {
            UserInfo userInfo1 = userInfo.get();
            userInfo1.setPassword(MD5Util.string2MD5(changePassDto.getNewPass()));
            userInfoRepository.save(userInfo1);
        } else {
            throw new UserManagerException("User with this username is not exists", HttpStatus.NO_CONTENT);
        }


    }


}
