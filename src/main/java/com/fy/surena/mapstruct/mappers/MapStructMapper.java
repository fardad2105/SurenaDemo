package com.fy.surena.mapstruct.mappers;

import com.fy.surena.mapstruct.dtos.UserInfoDto;
import com.fy.surena.mapstruct.dtos.UserInfoUpdateDto;
import com.fy.surena.model.UserInfo;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MapStructMapper {

    UserInfo userInfoPostToUserInfoDto(
            UserInfoDto userInfoDto
    );



}
