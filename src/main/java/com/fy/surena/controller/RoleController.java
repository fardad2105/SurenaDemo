package com.fy.surena.controller;

import com.fy.surena.exception.InputFieldException;
import com.fy.surena.mapstruct.dtos.request.RoleRequestDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.Role;
import com.fy.surena.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/role")
public class RoleController {

    public RoleController(RoleService roleService, MapStructMapper mapper) {
        this.roleService = roleService;
        this.mapper = mapper;
    }

    private final RoleService roleService;
    private final MapStructMapper mapper;



    @PostMapping("/add")
    public ResponseEntity<Void> addRole(@RequestBody RoleRequestDto roleRequestDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        roleService.saveRole(roleRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable int id,
                                           @RequestBody RoleRequestDto roleRequestDto) {
        roleService.findById(id)
                .map(role -> {
                    role.setId(id);
                    role.setTitle(roleRequestDto.getTitle());
                    role.setActive(roleRequestDto.isActive());
                    role.setDescription(roleRequestDto.getDescription());
                    role.setContent(roleRequestDto.getContent());
                    roleService.updateRole(mapper.roleDtoGetByRole(role));
                    return new ResponseEntity<Role>(HttpStatus.OK);
                })
                .orElseGet(() -> {
                    roleService.saveRole(roleRequestDto);
                    return new ResponseEntity<Role>(HttpStatus.OK);
                });
        return new ResponseEntity<Role>(HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleRequestDto>> getRoles() {
        List<RoleRequestDto> roles = roleService.getRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
