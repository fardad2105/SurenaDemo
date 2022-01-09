package com.fy.surena.service.Impl;

import com.fy.surena.config.Message;
import com.fy.surena.controller.UserInfoController;
import com.fy.surena.exception.UserManagerException;
import com.fy.surena.mapstruct.dtos.UserInfoDto;
import com.fy.surena.mapstruct.dtos.UserInfoUpdateDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.UserInfo;
import com.fy.surena.repository.UserInfoRepository;
import com.fy.surena.service.UserInfoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Transactional
class UserInfoServiceImplTest {


    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MapStructMapper mapStructMapper;

    private UserInfoDto user;

    private UserInfo savedUser;


    private UserInfoUpdateDto userInfoUpdateDto;

    @BeforeEach
    void setUp() {

        user = new UserInfoDto();
        user.setId(1L);
        user.setUsername("Sara2121");
        user.setPassword("147852369987");
        user.setFirstname("Sara");
        user.setLastname("Niazi");

    }

    @AfterEach
    void tearDown() {
        if (savedUser != null) {
            userInfoService.deleteUserInfoById(savedUser.getId());
        }
    }

    @Test
    void save() {
        savedUser = userInfoService.save(user);
        assertEquals(savedUser.getUsername(), user.getUsername());
    }

    @Test
    void save_failed() {
        savedUser = userInfoService.save(user);
        UserInfoDto userInfoDtoSaveFailed = new UserInfoDto();
        userInfoDtoSaveFailed.setId(1L);
        userInfoDtoSaveFailed.setUsername("Sara2121");
        userInfoDtoSaveFailed.setPassword("147852369987");
        userInfoDtoSaveFailed.setFirstname("Sara");
        userInfoDtoSaveFailed.setLastname("Niazi");
        Exception exception = assertThrows(UserManagerException.class, () -> {
            savedUser = userInfoService.save(userInfoDtoSaveFailed);

        });

        String expectedMessage = "User with this username:" + savedUser.getUsername() + " is Exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteUserInfoById() {
        savedUser = userInfoService.save(user);
        userInfoService.deleteUserInfoById(savedUser.getId());

        List<UserInfo> userInfos = userInfoService.getUsersInfo();
        List<UserInfo> userInfoList =  userInfos.stream()
                .filter(userInfo -> userInfo.getId().equals(savedUser.getId()))
                .collect(Collectors.toList());

        assertEquals(userInfoList.size(), 0);
        savedUser = null;
    }


    @Test
    void deleteUserInfoByUsername() {
        savedUser = userInfoService.save(user);
        userInfoService.deleteByUsername(savedUser.getUsername());

        List<UserInfo> userInfos = userInfoService.getUsersInfo();
        List<UserInfo> userInfoList =  userInfos.stream()
                .filter(userInfo -> userInfo.getUsername().equals(savedUser.getUsername()))
                .collect(Collectors.toList());

        assertEquals(userInfoList.size(), 0);
        savedUser = null;
    }

    @Test
    void editUserInfo() {
        savedUser = userInfoService.save(user);
        savedUser.setFirstname("SaSan");
        savedUser.setLastname("Zamaniiii");
        int update = userInfoService.EditUserInfo(savedUser.getFirstname(),savedUser.getLastname(),savedUser.getId());
        assertEquals(update, 1);

    }

    @Test
    void editWhenUserNotExist() {
        Exception exception = assertThrows(UserManagerException.class, () -> {
            userInfoService.EditUserInfo("Ali", "zamani", 5L);

        });

        String expectedMessage = "User with id: " + 5 + " is not exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void deleteWhenUserWithIdNotExists() {
        Exception exception = assertThrows(UserManagerException.class, () -> {
            userInfoService.deleteUserInfoById(5L);

        });

        String expectedMessage = "User with id: " + 5 + " is not exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteWhenUserWithUsernameNotExists() {

        Exception exception = assertThrows(UserManagerException.class, () -> {
            userInfoService.deleteByUsername("Soheil");

        });

        String expectedMessage = "User with username: Soheil is not exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getUserInfoById() {
        savedUser = userInfoService.save(user);
        UserInfoDto userInfoDto = userInfoService.getUserInfoById(savedUser.getId());
        assertEquals(savedUser.getUsername(),userInfoDto.getUsername());
    }

    @Test
    void getUserInfoByUserName() {
        savedUser = userInfoService.save(user);
        UserInfoDto userInfoDto = userInfoService.findByUsername(savedUser.getUsername());
        assertEquals(savedUser.getUsername(),userInfoDto.getUsername());
    }

    @Test
    void getUsersInfo() {
        savedUser = userInfoService.save(user);
        List<UserInfo> userInfos = userInfoService.getUsersInfo();
        assertTrue(userInfos.size() !=0);
    }




    private void checkErrorResponseStatusCode(UserManagerException exception, HttpStatus status) {
        assertNotNull(exception);
        HttpStatus statusCode = exception.getHttpStatus();
        assertNotNull(statusCode);
        assertEquals(status, statusCode);
        String message = exception.getMessage();
        assertNotNull(message);
    }

    private void checkResponseStatusCode(HttpStatus httpStatus, ResponseEntity<?> responseEntity) {
        assertNotNull(responseEntity);
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertNotNull(statusCode);
        assertEquals(httpStatus, statusCode);
    }

}