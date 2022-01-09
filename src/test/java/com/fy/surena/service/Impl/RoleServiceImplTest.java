package com.fy.surena.service.Impl;

import com.fy.surena.exception.PermissionManagementException;
import com.fy.surena.exception.RoleManagementException;
import com.fy.surena.mapstruct.dtos.request.RoleRequestDto;
import com.fy.surena.mapstruct.dtos.response.RoleResponseDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.Role;
import com.fy.surena.service.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Qualifier("mapStructMapperImpl")
    @Autowired
    private MapStructMapper mapper;

    private RoleRequestDto role;

    private RoleResponseDto savedRole;

    @BeforeEach
    void setUp() {
       role = new RoleRequestDto();
       role.setTitle("SUPER_ADMIN");
       role.setDescription("this is super-admin role");
       role.setContent("this role can do anything");
       role.setActive(true);
    }

    @AfterEach
    void tearDown() {
        if (savedRole != null) {
            roleService.deleteRole(savedRole.getId());
        }
    }

    @Test
    void saveRole() {
        savedRole = roleService.saveRole(role);
        assertEquals(savedRole.getTitle(),role.getTitle());
    }

    @Test
    void findById() {
        savedRole = roleService.saveRole(role);
        Optional<Role> findRole = roleService.findById(savedRole.getId());
        assertEquals(findRole.get().getTitle(),role.getTitle());
    }

    @Test
    void deleteRole() {
        RoleRequestDto deletedRole = new RoleRequestDto();
        deletedRole.setTitle("CLIENT");
        deletedRole.setDescription("this is client role");
        deletedRole.setContent("this role have read permission");
        deletedRole.setActive(true);
        RoleResponseDto dr = roleService.saveRole(deletedRole);
        roleService.deleteRole(dr.getId());

        Exception exception = assertThrows(RoleManagementException.class, () -> {
            roleService.deleteRole(dr.getId());

        });

        String expectedMessage = "Role with id: " + dr.getId() + "does not exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateRole() {
        savedRole = roleService.saveRole(role);
        savedRole.setActive(false);
        Role updatedRole = mapper.roleGetByRoleResponseDto(savedRole);
        RoleResponseDto updated = roleService.updateRole(savedRole.getId(),mapper.roleDtoGetByRole(updatedRole));
        assertFalse(updated.isActive());
    }


    @Test
    void getRoles() {
        List<Role> rolesBeforeAddedNewRole = roleService.getRoles();
        int countRoles = rolesBeforeAddedNewRole.size();
        savedRole = roleService.saveRole(role);
        List<Role> rolesAfterAddedNewRole = roleService.getRoles();
        int countRolesAfterUpdate = rolesAfterAddedNewRole.size();
        assertEquals(countRoles + 1, countRolesAfterUpdate);
    }

}