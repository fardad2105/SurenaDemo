package com.fy.surena.service.Impl;

import com.fy.surena.exception.RoleManagementException;
import com.fy.surena.mapstruct.dtos.request.RoleRequestDto;
import com.fy.surena.mapstruct.dtos.response.RoleResponseDto;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.Permission;
import com.fy.surena.model.Role;
import com.fy.surena.repository.RoleRepository;
import com.fy.surena.service.RoleService;
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

    public RoleServiceImpl(RoleRepository roleRepository, MapStructMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }


    @Override
    public Role saveRole(RoleRequestDto role) {
        if (roleRepository.existsByTitle(role.getTitle())) {
            throw new RoleManagementException(HttpStatus.CONFLICT,"Role with this" + role.getTitle() + "is Exists");
        } else {
            SimpleDateFormat create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            role.setCreateAtDate(create_date.format(now));
        }
        return roleRepository.save(mapper.roleGetByRoleRequestDto(role));
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
    public Role updateRole(RoleRequestDto role) {
        if (!roleRepository.existsByTitle(role.getTitle())) {
            throw new RoleManagementException(HttpStatus.NOT_FOUND,"Role with this info not found!");
        } else {
            SimpleDateFormat update_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            role.setUpdateAtDate(update_date.format(now));
            return roleRepository.save(mapper.roleGetByRoleRequestDto(role));
        }
    }

    @Override
    public List<RoleRequestDto> getRoles() {
        List<RoleRequestDto> roleResponseDtos = new ArrayList<>();
        for (Role role:
             roleRepository.findAll()) {
            roleResponseDtos.add(mapper.roleDtoGetByRole(role));
        }
        return roleResponseDtos;
    }
}
