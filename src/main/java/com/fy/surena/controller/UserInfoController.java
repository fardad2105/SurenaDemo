package com.fy.surena.controller;

import com.fy.surena.config.MD5Util;
import com.fy.surena.exception.InputFieldException;
import com.fy.surena.exception.UserExists;
import com.fy.surena.exception.UserIsNotDeleteException;
import com.fy.surena.mapstruct.dtos.ChangePassDto;
import com.fy.surena.mapstruct.dtos.UserInfoDto;
import com.fy.surena.mapstruct.dtos.UserInfoUpdateDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.UserInfo;
import com.fy.surena.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserInfoController {

    private final MapStructMapper mapStructMapper;

    private final UserInfoService userInfoService;

    @Value("${app.security_code}")
    private String encrypt;


    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody UserInfoDto userInfoDto,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        } else if (userInfoService.isExistUsername(userInfoDto.getUsername())) {
            throw new UserExists("User with this username:" + userInfoDto.getUsername() + "is Exists");
        } else {
            SimpleDateFormat create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            userInfoDto.setCreateDate(create_date.format(now));

            // Encrypt Password
            userInfoDto.setPassword(MD5Util.string2MD5(userInfoDto.getPassword()));


            String pwd=MD5Util.convertMD5(MD5Util.convertMD5(userInfoDto.getPassword()));
            System.out.println(pwd);

            userInfoService.save(
                    mapStructMapper.userInfoPostToUserInfoDto(userInfoDto)
            );
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        if (!userInfoService.isExistId(id)) {
            throw new UserIsNotDeleteException("User with id: " + id + "is not exists");
        }

        userInfoService.deleteUserInfoById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteByUsername(@PathVariable String username) {

        if (!userInfoService.isExistUsername(username)) {
            throw new UserIsNotDeleteException("User with id: " + username + "is not exists");
        }

        userInfoService.deleteUserInfoByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUserInfo(@PathVariable Long id,
                                               @RequestBody UserInfoUpdateDto userInfoUpdateDto) {
        if (!userInfoService.isExistId(id)) {
            throw new UserIsNotDeleteException("User with id: " + id + "is not exists");
        }
        SimpleDateFormat modify_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();

        userInfoService.EditUserInfo(userInfoUpdateDto.getFirstname(),
                userInfoUpdateDto.getLastname(), modify_date.format(now), id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/user/{id}")
    public UserInfo getUserInfoById(@PathVariable Long id) {

        if (!userInfoService.isExistId(id)) {
            throw new UserIsNotDeleteException("User with id: " + id + "is not exists");
        }
        return userInfoService.getUserInfoById(id);
    }

    @GetMapping("/get/{username}")
    public UserInfo getUserInfoByUsername(@PathVariable String username) {

        if (!userInfoService.isExistUsername(username)) {
            throw new UserIsNotDeleteException("User with id: " + username + "is not exists");
        }

        return userInfoService.getUserInfoByUserName(username);
    }

    @GetMapping("/all")
    public List<UserInfo> getAll() {
        return userInfoService.getUsersInfo();

    }

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePass(@RequestBody ChangePassDto changePassDto) {
        String newPassEncrypt = MD5Util.string2MD5(changePassDto.getNewPass());
        String oldPassEncrypt = MD5Util.string2MD5(changePassDto.getOldPass());

        userInfoService.changePassword(newPassEncrypt,oldPassEncrypt);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
