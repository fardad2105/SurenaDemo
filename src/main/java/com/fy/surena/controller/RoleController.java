package com.fy.surena.controller;

import com.fy.surena.exception.InputFieldException;
import com.fy.surena.mapstruct.dtos.request.RoleRequestDto;
import com.fy.surena.mapstruct.dtos.response.RoleResponseDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.Role;
import com.fy.surena.service.RoleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/role")
public class RoleController {

    public RoleController(RoleService roleService, @Qualifier("mapStructMapperImpl") MapStructMapper mapper) {
        this.roleService = roleService;
        this.mapper = mapper;
    }

    private final RoleService roleService;
    private final MapStructMapper mapper;



    @PostMapping("/add")
    public ResponseEntity<RoleResponseDto> addRole(@RequestBody RoleRequestDto roleRequestDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        RoleResponseDto savedRole =  roleService.saveRole(roleRequestDto);
        return ResponseEntity.ok(savedRole);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("deleted successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable int id,
                                           @RequestBody RoleRequestDto roleRequestDto) {
        RoleResponseDto roleResponseDto = roleService.updateRole(id, roleRequestDto);
        return ResponseEntity.ok(roleResponseDto);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.getRoles();
        return ResponseEntity.ok(roles);
    }
}
