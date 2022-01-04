package com.fy.surena.service.Impl;

import com.fy.surena.mapstruct.dtos.request.RoleRequestDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.Role;
import com.fy.surena.service.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MapStructMapper mapper;

    private RoleRequestDto role;

    private Role savedRole;

    @BeforeEach
    void setUp() {
       role = new RoleRequestDto();
       role.setTitle("SUPER_ADMIN");
       role.setDescription("this is super-admin role");
       role.setContent("this role can do anything");
       role.setActive(true);
       role.setCreateAtDate("");
       role.setUpdateAtDate("");
    }

    @AfterEach
    void tearDown() {
        if (savedRole != null) {
            roleService.deleteRole(savedRole.getId());
        }
    }

    @Test
    void saveRole() {
        SimpleDateFormat create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        role.setCreateAtDate(create_date.format(now));
        savedRole = roleService.saveRole(role);
    }

    @Test
    void findById() {
        Optional<Role> findRole = roleService.findById(1);
        assertEquals(findRole.get().getTitle(),"ADMIN");
    }

    @Test
    void deleteRole() {
        RoleRequestDto deletedRole = new RoleRequestDto();
        deletedRole.setTitle("CLIENT");
        deletedRole.setDescription("this is client role");
        deletedRole.setContent("this role have read permission");
        deletedRole.setActive(true);
        deletedRole.setCreateAtDate("");
        deletedRole.setUpdateAtDate("");
        Role dr = roleService.saveRole(deletedRole);
        roleService.deleteRole(dr.getId());
    }

    @Test
    void updateRole() {
        savedRole = roleService.saveRole(role);
        savedRole.setActive(false);
        Role updatedRole = roleService.updateRole(mapper.roleDtoGetByRole(savedRole));
        assertFalse(updatedRole.isActive());
    }


    @Test
    void getRoles() {
        List<RoleRequestDto> roleRequestDtos = roleService.getRoles();
        assertTrue(roleRequestDtos.size() != 0);
    }

}