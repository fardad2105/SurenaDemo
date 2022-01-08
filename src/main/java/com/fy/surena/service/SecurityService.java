package com.fy.surena.service;

import com.fy.surena.model.Role;

import java.util.Set;

public interface SecurityService {

    public void assignUserRole(long userId,int roleId);

    public void unassignUserRole(long userId, int roleId);

    public Set<Role> getUserRoles(long userId);

    public void addPermissionOnRole(int roleId, int permissionKey);

    public void removePermissionOnRole(int roleId, int permissionKey);

}
