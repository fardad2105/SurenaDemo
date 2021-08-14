package com.fy.surena.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fy.surena.mapstruct.dtos.UserInfoDto;
import com.fy.surena.mapstruct.dtos.UserInfoUpdateDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.mapstruct.mappers.MapStructMapperImpl;
import com.fy.surena.model.UserInfo;
import com.fy.surena.repository.UserInfoRepository;
import com.fy.surena.service.Impl.UserInfoServiceImpl;
import com.fy.surena.service.UserInfoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = {UserInfoController.class, MapStructMapperImpl.class})
@WebMvcTest(UserInfoController.class)
@AutoConfigureMockMvc
class UserInfoControllerTest {

    @Mock
    private UserInfoService userInfoService;
    private UserInfo userInfo;
    private List<UserInfo> userInfos;


    @MockBean
    private UserInfoController userInfoController;

    @Autowired
    private MockMvc mockMvc;

//
//    @MockBean
//    UserInfoService userInfoService;
//
//    @Mock
//    UserInfoRepository dao;

    @BeforeEach
    void setUp() {
        userInfo = new UserInfo(7L,"Soheil0101","5145897582","Soheil","zamaniiiii","","");
        mockMvc = MockMvcBuilders.standaloneSetup(userInfoController).build();
    }

    @AfterEach
    void tearDown() {
        userInfo = null;
    }

    @Test
    void create() throws Exception {
        when(userInfoService.save(any())).thenReturn(userInfo);
        mockMvc.perform(post("/api/users").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(userInfo))).
                andExpect(status().isOk()).andReturn();
//        verify(userInfoService,times(1)).save(any());
    }

    @Test
    void deleteById() {
        UserInfoDto userInfo = new UserInfoDto(2L,"Ali2020","1254695825148","Ali","Zamani","","");
        userInfoService.save(userInfo);
        userInfoService.deleteUserInfoById(userInfo.getId());
        UserInfoDto optional = userInfoService.getUserInfoById(userInfo.getId());
    }

    @Test
    void deleteByUsername() {
        UserInfoDto userInfo = new UserInfoDto(3L,"Nima2120","693256478525","Nima","Zarandi","","");
        userInfoService.save(userInfo);
        userInfoService.deleteByUsername(userInfo.getUsername());
        UserInfoDto userInfoDto = userInfoService.findByUsername(userInfo.getUsername());
        System.out.println(userInfoDto);
    }

    @Test
    void updateUserInfo() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                .put("/api/users/update/{id}", 7)
                .content(asJsonString(new UserInfoUpdateDto("Soheil", "Zamaniiiii")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Nima"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("Hosseini"));
    }

    @Test
    void getUserInfoById() throws Exception {
        UserInfoDto userInfo = new UserInfoDto(5l,"John2121","253614256985","John","Esmit","","");
//        userInfoService.save(userInfo);
//        UserInfo user = userInfoService.getUserInfoById(userInfo.getId());
//        System.out.println(user);
        when(userInfoService.getUserInfoById(eq(5l))).thenReturn(userInfo);
        mockMvc.perform( MockMvcRequestBuilders
                .get("/api/users/user/{id}", 5)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(7));
    }


    @Test
    void getAll() throws Exception {
        List<UserInfoDto> userInfos = Arrays.asList(new UserInfoDto(5l,"John2121","253614256985","John","Esmit","",""));
        when(userInfoService.getUsersInfo()).thenReturn(userInfos);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/all").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(userInfo))).
                andDo(print());

    }


    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private String getArticleInJson(long id) {
        return "{" +
                "\"id\":\"" + id + "\"," +
                " \"content\":\"test data\"" +
                " \"content\":\"test data\"" +
                "}";
    }
}