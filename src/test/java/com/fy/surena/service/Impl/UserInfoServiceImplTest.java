package com.fy.surena.service.Impl;

import com.fy.surena.controller.UserInfoController;
import com.fy.surena.exception.UserManagerException;
import com.fy.surena.mapstruct.dtos.UserInfoDto;
import com.fy.surena.mapstruct.dtos.UserInfoUpdateDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.UserInfo;
import com.fy.surena.repository.UserInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class UserInfoServiceImplTest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoController userInfoController;

    @Autowired
    private MapStructMapper mapStructMapper;

    private UserInfoDto userInfoDto;

    private UserInfoDto userInfoDtoSave;

    private UserInfo userInfo1;

    private UserInfoUpdateDto userInfoUpdateDto;

    @BeforeEach
    void setUp() {
        SimpleDateFormat create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();

        userInfoDtoSave = new UserInfoDto();
        userInfoDtoSave.setId(1L);
        userInfoDtoSave.setUsername("Sara2121");
        userInfoDtoSave.setPassword("147852369987");
        userInfoDtoSave.setFirstname("Sara");
        userInfoDtoSave.setLastname("Niazi");
        userInfoDtoSave.setCreateDate(create_date.format(now));
        userInfoDtoSave.setModifiedDate(create_date.format(now));


        userInfoDto = new UserInfoDto();
        userInfoDto.setId(1L);
        userInfoDto.setUsername("Saman1010");
        userInfoDto.setPassword("963852741");
        userInfoDto.setFirstname("Saman");
        userInfoDto.setLastname("Zamani");
        userInfoDto.setCreateDate(create_date.format(now));
        userInfoDto.setModifiedDate(create_date.format(now));
        UserInfo userInfo = mapStructMapper.userInfoPostToUserInfoDto(userInfoDto);
        userInfo1 = userInfoRepository.save(userInfo);

    }

    @AfterEach
    void tearDown() {
        userInfoRepository.deleteByUsername(userInfoDto.getUsername());
        userInfoRepository.deleteByUsername(userInfoDtoSave.getUsername());
    }

    @Test
    void save() {
        BindingResult result = mock(BindingResult.class);
        ResponseEntity<Void> responseEntity = userInfoController.create(userInfoDtoSave, result);
        checkResponseStatusCode(HttpStatus.CREATED,responseEntity);
    }

    @Test
    void deleteUserInfoById() {
        ResponseEntity<Void> responseEntity = userInfoController.deleteById(userInfo1.getId());
        checkResponseStatusCode(HttpStatus.OK,responseEntity);
    }

    @Test
    void deleteUserInfoByUsername() {
        ResponseEntity<Void> responseEntity = userInfoController.deleteByUsername(userInfo1.getUsername());
        checkResponseStatusCode(HttpStatus.OK,responseEntity);
    }

    @Test
    void editUserInfo() {
        userInfoUpdateDto = new UserInfoUpdateDto();
        userInfoUpdateDto.setFirstname("SaSan");
        userInfoUpdateDto.setLastname("Zamaniiii");
        ResponseEntity<UserInfoUpdateDto> responseEntity = userInfoController.
                updateUserInfo(userInfo1.getId(),userInfoUpdateDto);
        checkResponseStatusCode(HttpStatus.OK,responseEntity);
    }

    @Test
    void getUserInfoById() {
        ResponseEntity<UserInfo> responseEntity = userInfoController.getUserInfoById(userInfo1.getId());
        checkResponseStatusCode(HttpStatus.OK,responseEntity);
    }

    @Test
    void getUserInfoByUserName() {
        ResponseEntity<UserInfoDto> responseEntity = userInfoController.getUserInfoByUsername(userInfo1.getUsername());
        checkResponseStatusCode(HttpStatus.OK,responseEntity);
    }

    @Test
    void getUsersInfo() {
        ResponseEntity<List<UserInfo>> responseEntity = userInfoController.getAll();
        checkResponseStatusCode(HttpStatus.OK,responseEntity);
    }


    private void checkErrorResponseStatusCode(UserManagerException exception, HttpStatus status, String errorMessage) {
        assertNotNull(exception);
        HttpStatus statusCode = exception.getHttpStatus();
        assertNotNull(statusCode);
        assertEquals(status, statusCode);
        String message = exception.getMessage();
        assertNotNull(message);
        assertEquals(errorMessage, message);
    }

    private void checkResponseStatusCode(HttpStatus httpStatus, ResponseEntity<?> responseEntity) {
        assertNotNull(responseEntity);
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertNotNull(statusCode);
        assertEquals(httpStatus, statusCode);
    }

}