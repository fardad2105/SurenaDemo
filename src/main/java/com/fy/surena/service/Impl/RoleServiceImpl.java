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
            if (roleRepository.findById(id).isPresent()){
                return roleRepository.findById(id);
            } else {
                return Optional.empty();
            }
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
    public RoleResponseDto updateRole(int id, RoleRequestDto roleRequestDto) {
        if (!roleRepository.existsByTitle(roleRequestDto.getTitle())) {
            throw new RoleManagementException(HttpStatus.NOT_FOUND,"Role with this info not found!");
        } else {
            Optional<Role> findUpdatedRole = roleRepository.findById(id);
            if (findUpdatedRole.isPresent()) {
                findUpdatedRole.map(role -> {
                    role.setId(id);
                    role.setTitle(roleRequestDto.getTitle());
                    role.setActive(roleRequestDto.isActive());
                    role.setDescription(roleRequestDto.getDescription());
                    role.setContent(roleRequestDto.getContent());
                    Role updatedRole = roleRepository.save(role);
                    return mapper.getRoleResponseDto(updatedRole);
                }).orElseGet(() -> {
                    Role savedRole = roleRepository.save(mapper.roleGetByRoleRequestDto(roleRequestDto));
                    return mapper.getRoleResponseDto(savedRole);
                });
            } else {
                return null;
            }

        }
        return mapper.getRoleResponseDto(mapper.roleGetByRoleRequestDto(roleRequestDto));
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
