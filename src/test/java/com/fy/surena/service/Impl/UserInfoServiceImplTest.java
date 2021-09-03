package com.fy.surena.service.Impl;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoServiceImplTest {

    @Autowired
    private UserInfoRepository userInfoRepository;


    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MapStructMapper mapStructMapper;

    private UserInfoDto userInfoDtoUpdate;

    private UserInfoDto userInfoDtoSave;

    private UserInfo userInfo1;


    @BeforeEach
    void setUp() {
//        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
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


        userInfoDtoUpdate = new UserInfoDto();
        userInfoDtoUpdate.setId(1L);
        userInfoDtoUpdate.setUsername("Saman1010");
        userInfoDtoUpdate.setPassword("963852741");
        userInfoDtoUpdate.setFirstname("Saman");
        userInfoDtoUpdate.setLastname("Zamani");
        userInfoDtoUpdate.setCreateDate(create_date.format(now));
        userInfoDtoUpdate.setModifiedDate(create_date.format(now));
        UserInfo userInfo = mapStructMapper.userInfoPostToUserInfoDto(userInfoDtoUpdate);
        userInfo1 = userInfoRepository.save(userInfo);

    }

    @AfterEach
    void tearDown() {
        userInfoRepository.deleteByUsername(userInfoDtoUpdate.getUsername());
        userInfoRepository.deleteByUsername(userInfoDtoSave.getUsername());
    }

    @Test()
    void save() {
        UserInfo userInfo = userInfoService.save(userInfoDtoSave);
        assertEquals("Sara2121",userInfo.getUsername());
    }

    @Test
    void save_failed() {
        UserInfoDto userInfoDtoSaveFailed = new UserInfoDto();
        SimpleDateFormat create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();

        userInfoDtoSaveFailed.setId(1L);
        userInfoDtoSaveFailed.setUsername("Sara2121");
        userInfoDtoSaveFailed.setPassword("147852369987");
        userInfoDtoSaveFailed.setFirstname("Sara");
        userInfoDtoSaveFailed.setLastname("Niazi");
        userInfoDtoSaveFailed.setCreateDate(create_date.format(now));
        userInfoDtoSaveFailed.setModifiedDate(create_date.format(now));
        userInfoService.save(userInfoDtoSaveFailed);

    }

    @Test
    void deleteUserInfoById() {
        UserInfoDto test_delete_userInfo_ById = new UserInfoDto();
        test_delete_userInfo_ById.setId(20L);
        test_delete_userInfo_ById.setUsername("Ali2020");
        test_delete_userInfo_ById.setFirstname("Ali");
        test_delete_userInfo_ById.setLastname("Niazi");
        test_delete_userInfo_ById.setPassword("25654145632589");
        userInfoService.save(test_delete_userInfo_ById);
        UserInfoDto deleteUserInfo = userInfoService.findByUsername(test_delete_userInfo_ById.getUsername());
        userInfoService.deleteUserInfoById(deleteUserInfo.getId());
    }


    @Test
    void deleteUserInfoByUsername() {
        UserInfoDto test_delete_userInfo_ByUsername = new UserInfoDto();
        test_delete_userInfo_ByUsername.setUsername("Peter2020");
        test_delete_userInfo_ByUsername.setFirstname("Peter");
        test_delete_userInfo_ByUsername.setLastname("Jakson");
        test_delete_userInfo_ByUsername.setPassword("115446846464654");
        userInfoService.save(test_delete_userInfo_ByUsername);
        UserInfoDto deleteUserInfo = userInfoService.findByUsername(test_delete_userInfo_ByUsername.getUsername());
        userInfoService.deleteByUsername(deleteUserInfo.getUsername());
    }

    @Test
    void editUserInfo() {
        UserInfoUpdateDto userInfoUpdateDto = new UserInfoUpdateDto();
        userInfoUpdateDto.setFirstname("SaSan");
        userInfoUpdateDto.setLastname("Zamaniiii");

        int test_userInfo_update = userInfoService.EditUserInfo(
                userInfoUpdateDto.getFirstname(),
                userInfoUpdateDto.getLastname(),
                userInfo1.getId());
    }

    @Test
    void editWhenUserNotExist() {
        checkErrorResponseStatusCode(assertThrows(UserManagerException.class, () -> userInfoService.EditUserInfo("Ali","Zamaniiii",20L)),
                HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteWhenUserWithIdNotExists() {
        checkErrorResponseStatusCode(assertThrows(UserManagerException.class, ()-> userInfoService.getUserInfoById(20l)),
                HttpStatus.NOT_FOUND
                );
    }

    @Test
    void deleteWhenUserWithUsernameNotExists() {
        checkErrorResponseStatusCode(assertThrows(UserManagerException.class, () -> userInfoService.deleteByUsername("Ali2105")),
                HttpStatus.NOT_FOUND
                );
    }

    @Test
    void getUserInfoById() {
        UserInfoDto userInfoDto = userInfoService.getUserInfoById(userInfo1.getId());
        assertEquals(userInfo1.getUsername(),userInfoDto.getUsername());
    }

    @Test
    void getUserInfoByUserName() {
        UserInfoDto userInfoDto = userInfoService.findByUsername(userInfo1.getUsername());
        assertEquals(userInfo1.getUsername(),userInfoDto.getUsername());
    }

    @Test
    void getUsersInfo() {
        List<UserInfoDto> userInfos = userInfoService.getUsersInfo();
        assertEquals(2,userInfos.size());
    }




    private void checkErrorResponseStatusCode(UserManagerException exception, HttpStatus status) {
        assertNotNull(exception);
        HttpStatus statusCode = exception.getHttpStatus();
        assertNotNull(statusCode);
        assertEquals(status, statusCode);
        String message = exception.getMessage();
        assertNotNull(message);
    }

//    private void checkResponseStatusCode(HttpStatus httpStatus, ResponseEntity<?> responseEntity) {
//        assertNotNull(responseEntity);
//        HttpStatus statusCode = responseEntity.getStatusCode();
//        assertNotNull(statusCode);
//        assertEquals(httpStatus, statusCode);
//    }


}