package com.fy.surena.service.Impl;

import com.fy.surena.mapstruct.dtos.request.PermissionRequestDto;
import com.fy.surena.mapstruct.dtos.response.PermissionResponseDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.Permission;
import com.fy.surena.service.PermissionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("mapStructMapperImpl")
    @Autowired
    private MapStructMapper mapper;

    private PermissionRequestDto permissionRequestDto;

    private PermissionResponseDto savedPermission;

    @BeforeEach
    void setUp() {
        permissionRequestDto = new PermissionRequestDto();
        permissionRequestDto.setTitle("CHANGE");
        permissionRequestDto.setDescription("this is change permission");
        permissionRequestDto.setContent("this permission just can change somethings");
        permissionRequestDto.setActive(true);
    }

    @AfterEach
    void tearDown() {
        if (savedPermission != null) {
            permissionService.deletePermission(savedPermission.getId());
        }
    }

    @Test
    void savePermission() {
        savedPermission = permissionService.savePermission(permissionRequestDto);
        assertEquals(savedPermission.getTitle(),permissionRequestDto.getTitle());
    }

    @Test
    void findById() {
        savedPermission = permissionService.savePermission(permissionRequestDto);
        Optional<Permission> findPermission = permissionService.findById(savedPermission.getId());
        assertEquals(findPermission.get().getTitle(),savedPermission.getTitle());
    }

    @Test
    void deletePermission() {
        PermissionRequestDto deletePermission = new PermissionRequestDto();
        deletePermission.setTitle("CHANGE");
        deletePermission.setDescription("this is change permission");
        deletePermission.setContent("this permission just can change somethings");
        deletePermission.setActive(true);
        PermissionResponseDto dp = permissionService.savePermission(deletePermission);
        permissionService.deletePermission(dp.getId());
    }

    @Test
    void updatePermission() {
        savedPermission = permissionService.savePermission(permissionRequestDto);
        savedPermission.setActive(false);
        Permission permission = mapper.permissionGetByPermissionResponseDto(savedPermission);
        PermissionResponseDto updatePermission = permissionService.updatePermission(mapper.permissionDtoGetBypermission(permission));
        assertFalse(updatePermission.isActive());
    }

    @Test
    void getPermissions() {
        List<PermissionResponseDto> permissions = permissionService.getPermissions();
        assertTrue(permissions.size() != 0);
    }
}