package com.fy.surena.service.Impl;

import com.fy.surena.mapstruct.dtos.request.PermissionRequestDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.Permission;
import com.fy.surena.service.PermissionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PermissionServiceImplTest {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MapStructMapper mapper;

    private PermissionRequestDto permissionRequestDto;

    private Permission savedPermission;

    @BeforeEach
    void setUp() {
        permissionRequestDto = new PermissionRequestDto();
        permissionRequestDto.setTitle("CHANGE");
        permissionRequestDto.setDescription("this is change permission");
        permissionRequestDto.setContent("this permission just can change somethings");
        permissionRequestDto.setActive(true);
        permissionRequestDto.setCreatedAt("");
        permissionRequestDto.setUpdatedAt("");
    }

    @AfterEach
    void tearDown() {
        if (savedPermission != null) {
            permissionService.deletePermission(savedPermission.getId());
        }
    }

    @Test
    void savePermission() {
        SimpleDateFormat create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        permissionRequestDto.setCreatedAt(create_date.format(now));
        savedPermission = permissionService.savePermission(permissionRequestDto);
    }

    @Test
    void findById() {
        //NOTICE: for test this method first your added item in permission must have READ title
        // else please change title in assertEquals
        Optional<Permission> findPermission = permissionService.findById(1);
        assertEquals(findPermission.get().getTitle(),"READ");
    }

    @Test
    void deletePermission() {
        PermissionRequestDto deletePermission = new PermissionRequestDto();
        deletePermission.setTitle("CHANGE");
        deletePermission.setDescription("this is change permission");
        deletePermission.setContent("this permission just can change somethings");
        deletePermission.setActive(true);
        deletePermission.setCreatedAt("");
        deletePermission.setUpdatedAt("");
        Permission dp = permissionService.savePermission(deletePermission);
        permissionService.deletePermission(dp.getId());
    }

    @Test
    void updatePermission() {
        savedPermission = permissionService.savePermission(permissionRequestDto);
        savedPermission.setActive(false);
        Permission updatePermission = permissionService.updatePermission(mapper.permissionDtoGetBypermission(savedPermission));
        assertFalse(updatePermission.isActive());
    }

    @Test
    void getPermissions() {
        List<Permission> permissions = permissionService.getPermissions();
        assertTrue(permissions.size() != 0);
    }
}