package com.fy.surena.service;

import com.fy.surena.model.Permission;
import com.fy.surena.mapstruct.dtos.request.PermissionRequestDto;
import com.fy.surena.mapstruct.dtos.response.PermissionResponseDto;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    Permission savePermission(PermissionRequestDto permission);
    Optional<Permission> findById(int id);
    void deletePermission(int id);
    Permission updatePermission(PermissionRequestDto permission);
    List<Permission> getPermissions();
}
