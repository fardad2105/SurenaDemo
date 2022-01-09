package com.fy.surena.mapstruct.mappers;

import com.fy.surena.mapstruct.dtos.UserInfoDto;
import com.fy.surena.mapstruct.dtos.request.RoleRequestDto;
import com.fy.surena.mapstruct.dtos.response.PermissionResponseDto;
import com.fy.surena.mapstruct.dtos.response.RoleResponseDto;
import com.fy.surena.model.Permission;
import com.fy.surena.model.Role;
import com.fy.surena.model.UserInfo;
import com.fy.surena.mapstruct.dtos.request.PermissionRequestDto;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface MapStructMapper {

    UserInfo userInfoPostToUserInfoDto(
            UserInfoDto userInfoDto
    );

    UserInfoDto userInfoDtoGetByUserInfo(
            UserInfo userInfo
    );

    PermissionRequestDto permissionDtoGetBypermission(
            Permission permission
    );

    Permission permissionGetBypermissionDto(
            PermissionRequestDto permissionRequestDto
    );

    PermissionResponseDto getPermissionResponseFromPermission(
            Permission permission
    );

    PermissionResponseDto getPermissionResponseDto(
            Role role
    );

    Permission permissionGetByPermissionResponseDto(
            PermissionResponseDto permission
    );

    RoleRequestDto roleDtoGetByRole(
            Role role
    );

    Role roleGetByRoleRequestDto(
            RoleRequestDto roleRequestDto
    );

    Role roleGetByRoleResponseDto (
            RoleResponseDto roleResponseDto
    );

    RoleResponseDto getRoleResponseDto(
            Role role
    );


}
