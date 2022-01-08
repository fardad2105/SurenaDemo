package com.fy.surena.service.Impl;

import com.fy.surena.exception.RoleManagementException;
import com.fy.surena.mapstruct.dtos.request.RoleRequestDto;
import com.fy.surena.mapstruct.dtos.response.RoleResponseDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.Permission;
import com.fy.surena.model.Role;
import com.fy.surena.repository.RoleRepository;
import com.fy.surena.service.RoleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    private MapStructMapper mapper;

    public RoleServiceImpl(RoleRepository roleRepository, @Qualifier("mapStructMapperImpl") MapStructMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }


    @Override
    public RoleResponseDto saveRole(RoleRequestDto role) {
        if (roleRepository.existsByTitle(role.getTitle())) {
            throw new RoleManagementException(HttpStatus.CONFLICT,"Role with this" + role.getTitle() + "is Exists");
        } else {
            Role savedRole =  roleRepository.save(mapper.roleGetByRoleRequestDto(role));
            return mapper.getRoleResponseDto(savedRole);
        }
    }

    @Override
    public Optional<Role> findById(int id) {
        if (!roleRepository.existsById(id)) {
            throw new RoleManagementException(HttpStatus.NOT_FOUND,"Role with id: " + id + "does not exists");
        } else {
            return roleRepository.findById(id);
        }
    }


    @Override
    public void deleteRole(int id) {
        if (!roleRepository.existsById(id)) {
            throw new RoleManagementException(HttpStatus.NOT_FOUND,"Role with id: " + id + "does not exists");
        } else {
            roleRepository.deleteById(id);
        }
    }

    @Override
    public RoleResponseDto updateRole(RoleRequestDto role) {
        if (!roleRepository.existsByTitle(role.getTitle())) {
            throw new RoleManagementException(HttpStatus.NOT_FOUND,"Role with this info not found!");
        } else {
            Role updatedRole = roleRepository.save(mapper.roleGetByRoleRequestDto(role));
            return mapper.getRoleResponseDto(updatedRole);
        }
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
