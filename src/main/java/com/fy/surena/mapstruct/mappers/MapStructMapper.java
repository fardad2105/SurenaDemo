package com.fy.surena.mapstruct.mappers;

import com.fy.surena.mapstruct.dtos.UserInfoDto;
import com.fy.surena.mapstruct.dtos.UserInfoUpdateDto;
import com.fy.surena.model.UserInfo;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
@Qualifier
public interface MapStructMapper {

    UserInfo userInfoPostToUserInfoDto(
            UserInfoDto userInfoDto
    );

    UserInfoDto userInfoDtoGetByUserInfo(
            UserInfo userInfo
    );



}
