package com.fy.surena.mapstruct.dtos.response;

import com.fy.surena.mapstruct.dtos.request.RoleRequestDto;
import com.fy.surena.model.Permission;

import java.util.List;
import java.util.Set;

public class RoleResponseDto extends RoleRequestDto {

    private int id;
    private List<Permission> permissions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Permission> getPermissionSet() {
        return permissions;
    }

    public void setPermissionSet(List<Permission> permissionSet) {
        this.permissions = permissionSet;
    }
}
