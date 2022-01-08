package com.fy.surena.service;

import com.fy.surena.mapstruct.dtos.request.RoleRequestDto;
import com.fy.surena.mapstruct.dtos.response.RoleResponseDto;
import com.fy.surena.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    RoleResponseDto saveRole(RoleRequestDto role);
    Optional<Role> findById(int id);
    void deleteRole(int id);
    RoleResponseDto updateRole(RoleRequestDto role);
    List<Role> getRoles();
}
