package com.fy.surena.service.Impl;

import com.fy.surena.mapstruct.dtos.UserInfoDto;
import com.fy.surena.mapstruct.dtos.request.PermissionRequestDto;
import com.fy.surena.mapstruct.dtos.request.RoleRequestDto;
import com.fy.surena.mapstruct.dtos.response.PermissionResponseDto;
import com.fy.surena.mapstruct.dtos.response.RoleResponseDto;
import com.fy.surena.model.Permission;
import com.fy.surena.model.Role;
import com.fy.surena.model.UserInfo;
import com.fy.surena.service.PermissionService;
import com.fy.surena.service.RoleService;
import com.fy.surena.service.SecurityService;
import com.fy.surena.service.UserInfoService;
import com.sun.xml.bind.v2.TODO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityServiceImplTest {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    private UserInfoDto userInfo;
    private RoleRequestDto role;
    private PermissionRequestDto permission;

    private UserInfo savedUserInfo;
    private RoleResponseDto savedRole;
    private PermissionResponseDto savedPermission;

    @BeforeEach
    void setUp() {

        // Initialize user
        userInfo = new UserInfoDto();
        userInfo.setUsername("Sara1010");
        userInfo.setPassword("123443210");
        userInfo.setFirstname("Sara");
        userInfo.setLastname("Abbasi");
        savedUserInfo = userInfoService.save(userInfo);

        // Initialize role
        role = new RoleRequestDto();
        role.setTitle("TESTROLE");
        role.setDescription("this is test role");
        role.setContent("this role have no any permission");
        role.setActive(false);
        savedRole = roleService.saveRole(role);

        // Initialize permission
        permission = new PermissionRequestDto();
        permission.setTitle("TESTPERMISSION");
        permission.setDescription("this is testpermission");
        permission.setContent("this permission can't do somethings");
        permission.setActive(false);
        savedPermission = permissionService.savePermission(permission);
    }

    @AfterEach
    void tearDown() {
        permissionService.deletePermission(savedPermission.getId());
        roleService.deleteRole(savedRole.getId());
        userInfoService.deleteUserInfoById(savedUserInfo.getId());
    }


    @Test
    void assignUserRole() {
        securityService.assignUserRole(savedUserInfo.getId(),savedRole.getId());
        Set<Role> roles = securityService.getUserRoles(savedUserInfo.getId());
        assertTrue(roles.size() != 0);
        securityService.unassignUserRole(savedUserInfo.getId(),savedRole.getId());
    }

    @Test
    void unassignUserRole() {
        securityService.assignUserRole(savedUserInfo.getId(),savedRole.getId());
        Set<Role> roles = securityService.getUserRoles(savedUserInfo.getId());
        assertTrue(roles.size() != 0);
        securityService.unassignUserRole(savedUserInfo.getId(),savedRole.getId());
        Set<Role> roless = securityService.getUserRoles(savedUserInfo.getId());
        assertEquals(0, roless.size());
    }

    @Test
    void getUserRoles() {
        securityService.assignUserRole(savedUserInfo.getId(),savedRole.getId());
        Set<Role> roles = securityService.getUserRoles(savedUserInfo.getId());
        assertTrue(roles.size() != 0);
        securityService.unassignUserRole(savedUserInfo.getId(),savedRole.getId());
    }

    @Test
    void addPermissionOnRole() {
        securityService.addPermissionOnRole(savedRole.getId(),savedPermission.getId());
        Set<Permission> permissions = roleService.findById(savedRole.getId()).get().getPermissions();
        assertTrue(permissions.size() != 0);
        securityService.removePermissionOnRole(savedRole.getId(),savedPermission.getId());
    }

    @Test
    void removePermissionOnRole() {
        Set<Permission> permissions;
       securityService.addPermissionOnRole(savedRole.getId(),savedPermission.getId());
       permissions = roleService.findById(savedRole.getId()).get().getPermissions();
        assertTrue(permissions.size() != 0);
        securityService.removePermissionOnRole(savedRole.getId(),savedPermission.getId());
        permissions = roleService.findById(savedRole.getId()).get().getPermissions();
        assertEquals(0, permissions.size());

    }
}