package com.fy.surena.controller;

import com.fy.surena.exception.InputFieldException;
import com.fy.surena.mapstruct.dtos.ChangePassDto;
import com.fy.surena.mapstruct.dtos.UserInfoDto;
import com.fy.surena.mapstruct.dtos.UserInfoUpdateDto;
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
    public ResponseEntity<UserInfoDto> create(@Valid @RequestBody UserInfoDto userInfoDto,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new InputFieldException(bindingResult);
        userInfoService.save(userInfoDto);
        return new ResponseEntity<>(userInfoDto,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        userInfoService.deleteUserInfoById(id);
        return new ResponseEntity<>("User is deleted",HttpStatus.OK);
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
    public ResponseEntity<List<UserInfoDto>> getAll() {
        List<UserInfoDto> userInfos = userInfoService.getUsersInfo();
        return new ResponseEntity<List<UserInfoDto>>(userInfos, HttpStatus.OK);

    }

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePass(@RequestBody ChangePassDto changePassDto) {
        userInfoService.changePassword(changePassDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
