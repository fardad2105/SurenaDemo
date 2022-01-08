package com.fy.surena.service.Impl;

import com.fy.surena.exception.PermissionManagementException;
import com.fy.surena.exception.RoleManagementException;
import com.fy.surena.exception.UserManagerException;
import com.fy.surena.model.Permission;
import com.fy.surena.model.Role;
import com.fy.surena.model.UserInfo;
import com.fy.surena.repository.PermissionRepository;
import com.fy.surena.repository.RoleRepository;
import com.fy.surena.repository.UserInfoRepository;
import com.fy.surena.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

    private UserInfoRepository userInfoRepository;
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    public SecurityServiceImpl(UserInfoRepository userInfoRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.userInfoRepository = userInfoRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void assignUserRole(long userId, int roleId) {
        if (!userInfoRepository.findById(userId).isPresent()) {
            throw new UserManagerException("User not found with this id"+ userId, HttpStatus.NOT_FOUND);
        } else {
            UserInfo userInfo = userInfoRepository.findById(userId).orElse(null);
            Role role = roleRepository.findById(roleId).orElse(null);
            Set<Role> userRoles = userInfo.getRoles();
            userRoles.add(role);
            userInfo.setRoles(userRoles);
            userInfoRepository.save(userInfo);
        }

    }

    @Override
    public void unassignUserRole(long userId, int roleId) {
        if (!userInfoRepository.findById(userId).isPresent()) {
            throw new UserManagerException("User not found with this id "+ userId, HttpStatus.NOT_FOUND);
        } else {
            UserInfo userInfo = userInfoRepository.findById(userId).get();
            userInfo.getRoles().removeIf(role -> role.getId() == roleId);
            userInfoRepository.save(userInfo);
        }
    }

    @Override
    public Set<Role> getUserRoles(long userId) {
        if (!userInfoRepository.findById(userId).isPresent()) {
            throw new UserManagerException("User not found with this id " + userId, HttpStatus.NOT_FOUND);
        } else {
            Optional<UserInfo> userInfo = userInfoRepository.findById(userId);
            return userInfo.get().getRoles();
        }

    }

    @Override
    public void addPermissionOnRole(int roleId, int permissionKey) {

        // check role
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleManagementException(HttpStatus.NOT_FOUND,String.format("Role not found with Id = %s", roleId));
        }
        Role role = roleOpt.get();

        // check if exists the permission key
        Permission permission;

        Optional<Permission> permissionOpt = permissionRepository.findById(permissionKey);
        if (permissionOpt.isPresent()) {
            // the permission exists
            permission = permissionOpt.get();
        } else {
            // if the permission doesn't exists: create one as default just read permission
            permission = new Permission();
            permission.setTitle("READ");

            permission = permissionRepository.save(permission);
        }

        // check if this role contains already the given permission
        if (role.getPermissions().contains(permission)) {
            throw new PermissionManagementException(HttpStatus.CONFLICT,String.format("The permission %s has been already" +
                    " associated on the role %s", permission.getTitle(), role.getTitle() ));
        }

        role.getPermissions().add(permission);
        roleRepository.save(role);

        roleRepository.findById(roleId).get();
    }

    @Override
    public void removePermissionOnRole(int roleId, int permissionKey) {

        // check role
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleManagementException(HttpStatus.NOT_FOUND,String.format("Role not found with Id = %s", roleId));
        }
        Role role = roleOpt.get();

        // check permission
        Optional<Permission> permissionOpt = permissionRepository.findById(permissionKey);
        if (!permissionOpt.isPresent()) {
            throw new PermissionManagementException(HttpStatus.NOT_FOUND,String.format("Permission not found with Id = %s on role %s",
                    permissionKey, roleId));
        }

        Permission permission = permissionOpt.get();

        role.getPermissions().remove(permission);
        roleRepository.save(role);

        roleRepository.findById(roleId).get();
    }


}
