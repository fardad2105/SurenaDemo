package com.fy.surena.controller;

import com.fy.surena.exception.InputFieldException;
import com.fy.surena.mapstruct.dtos.ChangePassDto;
import com.fy.surena.mapstruct.dtos.UserInfoDto;
import com.fy.surena.mapstruct.dtos.UserInfoUpdateDto;
import com.fy.surena.model.Permission;
import com.fy.surena.model.UserInfo;
import com.fy.surena.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserInfoController {

    private final UserInfoService userInfoService;


    @PostMapping
    public ResponseEntity<UserInfo> create(@Valid @RequestBody UserInfoDto userInfoDto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new InputFieldException(bindingResult);
        UserInfo userInfo = userInfoService.save(userInfoDto);
        return  ResponseEntity.ok(userInfo);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userInfoService.deleteUserInfoById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteByUsername(@RequestParam(value = "username") String username) {
        userInfoService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<UserInfoUpdateDto> updateUserInfo(@PathVariable Long id,
                                                            @RequestBody UserInfoUpdateDto userInfoUpdateDto) {
        userInfoService.EditUserInfo(userInfoUpdateDto.getFirstname(),
                userInfoUpdateDto.getLastname(), id);
        return new ResponseEntity<>(userInfoUpdateDto, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserInfoDto> getUserInfoById(@PathVariable Long id) {
        UserInfoDto userInfo = userInfoService.getUserInfoById(id);
        return new ResponseEntity<UserInfoDto>(userInfo, HttpStatus.OK);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<UserInfoDto> getUserInfoByUsername(@PathVariable String username) {
        UserInfoDto userInfoDto = userInfoService.findByUsername(username);
        return new ResponseEntity<>(userInfoDto,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserInfo>> getAll() {
        List<UserInfo> userInfos = userInfoService.getUsersInfo();
        return ResponseEntity.ok(userInfos);

    }

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePass(@RequestBody ChangePassDto changePassDto) {
        userInfoService.changePassword(changePassDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
