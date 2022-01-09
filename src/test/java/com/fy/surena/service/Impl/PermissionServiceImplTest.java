package com.fy.surena.service.Impl;

import com.fy.surena.exception.PermissionManagementException;
import com.fy.surena.exception.UserManagerException;
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

        Exception exception = assertThrows(PermissionManagementException.class, () -> {
            permissionService.deletePermission(dp.getId());

        });

        String expectedMessage = "permission with this" + dp.getId() + "does not exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updatePermission() {
        savedPermission = permissionService.savePermission(permissionRequestDto);
        savedPermission.setActive(false);
        Permission updatedPermission = mapper.permissionGetByPermissionResponseDto(savedPermission);
        PermissionResponseDto updatePermission = permissionService.updatePermission(savedPermission.getId(),
                mapper.permissionDtoGetBypermission(updatedPermission));
        assertFalse(updatePermission.isActive());
    }

    @Test
    void getPermissions() {
        List<PermissionResponseDto> permissions = permissionService.getPermissions();
        int countPermissions = permissions.size();
        savedPermission = permissionService.savePermission(permissionRequestDto);
        List<PermissionResponseDto> permissionsAfterUpdate = permissionService.getPermissions();
        int countPermissionAfterUpdate = permissionsAfterUpdate.size();

        assertEquals(countPermissionAfterUpdate, countPermissions + 1);
    }
}