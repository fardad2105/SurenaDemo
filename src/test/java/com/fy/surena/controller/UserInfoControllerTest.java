package com.fy.surena.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fy.surena.exception.UserManagerException;
import com.fy.surena.exception.UserNotFoundException;
import com.fy.surena.mapstruct.dtos.UserInfoDto;
import com.fy.surena.mapstruct.dtos.UserInfoUpdateDto;
import com.fy.surena.service.UserInfoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserInfoController.class)
@AutoConfigureMockMvc
class UserInfoControllerTest {

    @MockBean
    private UserInfoService userInfoService;

    private UserInfoDto userInfo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        userInfo = new UserInfoDto(7L, "Soheil0101", "5145897582", "Soheil", "zamaniiiii", "", "");
    }

    @AfterEach
    void tearDown() {
        userInfo = null;
    }

    @Test
    void create() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/users").
                accept(MediaType.APPLICATION_JSON_VALUE).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(objectMapper.writeValueAsString(userInfo))).
                andExpect(status().isCreated()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(userInfo);
        assertThat(objectMapper.writeValueAsString(actualResponseBody)).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedResponseBody)
        );
    }

    @Test
    void deleteById() throws Exception {
        Long deleteId = 1L;
        Mockito.doNothing().when(userInfoService).deleteUserInfoById(deleteId);
        mockMvc.perform(delete("/api/users/delete/"+deleteId)).andExpect(status().isOk());
        Mockito.verify(userInfoService,times(1)).deleteUserInfoById(deleteId);
    }

    @Test
    void deleteByUsername() throws Exception {
        String username = "Ali2020";
        Mockito.doNothing().when(userInfoService).deleteByUsername(username);
        mockMvc.perform(delete("/api/users/delete").param("username",username)).andExpect(status().isOk());
        Mockito.verify(userInfoService,times(1)).deleteByUsername(username);
    }

    @Test
    void updateUserInfo() throws Exception {

        UserInfoUpdateDto updateDto = new UserInfoUpdateDto("Soheil","Zamaniiiii");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/users/update/{id}", 7)
                .content(asJsonString(updateDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(updateDto);
        assertThat(objectMapper.writeValueAsString(actualResponseBody)).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedResponseBody)
        );
    }

    @Test
    void getUserInfoById() throws Exception {
        when(userInfoService.getUserInfoById(anyLong())).thenReturn(userInfo);

        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users/user/{id}", 1)
                .content(asJsonString(userInfo))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(userInfo);
        assertThat(objectMapper.writeValueAsString(actualResponseBody)).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedResponseBody)
        );
    }


    @Test
    void getAll() throws Exception {
        List<UserInfoDto> userInfos = Arrays.asList(new UserInfoDto(5l, "John2121", "253614256985", "John", "Esmit", "", ""));
        when(userInfoService.getUsersInfo()).thenReturn(userInfos);
        MvcResult mvcResult = mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(userInfos);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

    }

    @Test
    public void whenUserNotFound() {
        when(userInfoService.getUserInfoById(anyLong())).thenReturn(null);

        doThrow(new UserNotFoundException("user not found",HttpStatus.NOT_FOUND))
               .when(userInfoService).getUserInfoById(1L);

    }

    private void checkErrorResponseStatusCode(UserManagerException exception, HttpStatus status) {
        assertNotNull(exception);
        HttpStatus statusCode = exception.getHttpStatus();
        assertNotNull(statusCode);
        assertEquals(status, statusCode);
        String message = exception.getMessage();
        assertNotNull(message);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}