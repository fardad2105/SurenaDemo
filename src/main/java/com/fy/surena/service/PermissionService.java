package com.fy.surena.service;

import com.fy.surena.model.Permission;
import com.fy.surena.mapstruct.dtos.request.PermissionRequestDto;
import com.fy.surena.mapstruct.dtos.response.PermissionResponseDto;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    PermissionResponseDto savePermission(PermissionRequestDto permission);
    Optional<Permission> findById(int id);
    void deletePermission(int id);
    PermissionResponseDto updatePermission(int id, PermissionRequestDto permission);
    List<PermissionResponseDto> getPermissions();
    List<Permission> getUserPermissions(long userId);
}
