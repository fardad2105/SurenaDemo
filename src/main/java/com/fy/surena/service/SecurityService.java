package com.fy.surena.service;

import com.fy.surena.model.Permission;
import com.fy.surena.model.Role;
import com.fy.surena.model.UserInfo;

import java.util.List;
import java.util.Set;

public interface SecurityService {

    public void assignUserRole(long userId,int roleId);

    public void unassignUserRole(long userId, int roleId);

    public Set<Role> getUserRoles(long userId);

//    public List<Permission> getUserPermissions(long userId);

    public Role addPermissionOnRole(int roleId,int permissionKey);

    public Role removePermissionOnRole(int roleId, int permissionKey);

}
