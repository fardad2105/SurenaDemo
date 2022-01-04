package com.fy.surena.controller;


import com.fy.surena.exception.InputFieldException;
import com.fy.surena.mapstruct.dtos.request.PermissionRequestDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.Permission;
import com.fy.surena.service.PermissionService;
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

    public PermissionController(PermissionService permissionService, MapStructMapper mapper) {
        this.permissionService = permissionService;
        this.mapper = mapper;
    }


    @PostMapping("/add")
    public ResponseEntity<Void> addPermission(@RequestBody PermissionRequestDto permissionRequestDto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        permissionService.savePermission(permissionRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        permissionService.deletePermission(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable int id,
                                                       @RequestBody PermissionRequestDto permissionRequestDto) {
        permissionService.findById(id)
                .map(permission -> {
                    permission.setId(id);
                    permission.setTitle(permissionRequestDto.getTitle());
                    permission.setActive(permissionRequestDto.isActive());
                    permission.setDescription(permissionRequestDto.getDescription());
                    permission.setContent(permissionRequestDto.getContent());
                    permissionService.updatePermission(mapper.permissionDtoGetBypermission(permission));
                    return new ResponseEntity<Permission>(HttpStatus.OK);
                })
                .orElseGet(() -> {
                    permissionService.savePermission(permissionRequestDto);
                    return new ResponseEntity<Permission>(HttpStatus.OK);
                });

        return new ResponseEntity<Permission>(HttpStatus.OK);
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>> getPermissions() {
        List<Permission> permissions = permissionService.getPermissions();
        return new ResponseEntity<List<Permission>>(permissions,HttpStatus.OK);
    }
}
