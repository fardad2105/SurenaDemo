package com.fy.surena.controller;


import com.fy.surena.exception.InputFieldException;
import com.fy.surena.mapstruct.dtos.request.PermissionRequestDto;
import com.fy.surena.mapstruct.dtos.response.PermissionResponseDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.Permission;
import com.fy.surena.service.PermissionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/permission")
public class PermissionController {

    private final PermissionService permissionService;
    private final MapStructMapper mapper;

    public PermissionController(PermissionService permissionService, @Qualifier("mapStructMapperImpl") MapStructMapper mapper) {
        this.permissionService = permissionService;
        this.mapper = mapper;
    }


    @PostMapping("/add")
    public ResponseEntity<PermissionResponseDto> addPermission(@RequestBody PermissionRequestDto permissionRequestDto,
                                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        PermissionResponseDto addedPermission = permissionService.savePermission(permissionRequestDto);
        return ResponseEntity.ok(addedPermission);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok("deleted successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PermissionResponseDto> updatePermission(@PathVariable int id,
                                                       @RequestBody PermissionRequestDto permissionRequestDto) {
       PermissionResponseDto updatedPermission = permissionService.updatePermission(id, permissionRequestDto);
       return ResponseEntity.ok(updatedPermission);
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionResponseDto>> getPermissions() {
        List<PermissionResponseDto> permissions = permissionService.getPermissions();
        return ResponseEntity.ok(permissions);
    }


    @GetMapping("/get-user-permission/{userId}")
    public ResponseEntity<List<Permission>> getUserPermissions(@PathVariable("userId") Long userId) {
        List<Permission> permissions =  permissionService.getUserPermissions(userId);
        return new ResponseEntity<>(permissions,HttpStatus.OK);
    }
}
