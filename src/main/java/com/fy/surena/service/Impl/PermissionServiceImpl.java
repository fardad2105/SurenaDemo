package com.fy.surena.service.Impl;

import com.fy.surena.exception.PermissionManagementException;
import com.fy.surena.mapstruct.mappers.MapStructMapper;
import com.fy.surena.model.Permission;
import com.fy.surena.mapstruct.dtos.request.PermissionRequestDto;
import com.fy.surena.mapstruct.dtos.response.PermissionResponseDto;
import com.fy.surena.repository.PermissionRepository;
import com.fy.surena.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private PermissionRepository permissionRepository;

    private MapStructMapper mapper;

    public PermissionServiceImpl(PermissionRepository permissionRepository, MapStructMapper mapStructMapper) {
        this.permissionRepository = permissionRepository;
        this.mapper = mapStructMapper;
    }

    @Override
    public PermissionResponseDto savePermission(PermissionRequestDto permission) {
        if (permissionRepository.existsByTitle(permission.getTitle())) {
            throw new PermissionManagementException(HttpStatus.CONFLICT,
                    "permission with this" + permission.getTitle() + "is Exists");
        } else {
            Permission savedPermission = permissionRepository.save(mapper.permissionGetBypermissionDto(permission));
            return mapper.getPermissionResponseFromPermission(savedPermission);
        }
    }

    @Override
    public Optional<Permission> findById(int id) {
        if (!permissionRepository.existsById(id)) {
            throw new PermissionManagementException(HttpStatus.NOT_FOUND,
                    "permission with this" + id + "does not exists");
        } else {
            return permissionRepository.findById(id);
        }
    }


    @Override
    public void deletePermission(int id) {
        if (!permissionRepository.existsById(id)) {
            throw new PermissionManagementException(HttpStatus.NOT_FOUND,
                    "permission with this" + id + "does not exists");
        } else {
            permissionRepository.deleteById(id);
        }
    }

    @Override
    public PermissionResponseDto updatePermission(PermissionRequestDto permission) {
        if (!permissionRepository.existsByTitle(permission.getTitle())) {
            throw new PermissionManagementException(HttpStatus.NOT_FOUND,
                    "permission with this info not found");
        } else {
            Permission updatedPermission =  permissionRepository.save(mapper.permissionGetBypermissionDto(permission));
            return mapper.getPermissionResponseFromPermission(updatedPermission);
        }
    }

    @Override
    public List<PermissionResponseDto> getPermissions() {
        List<PermissionResponseDto> permissionResponseDtos = new ArrayList<>();
        for (Permission pr:
             permissionRepository.findAll()) {
             permissionResponseDtos.add(mapper.getPermissionResponseFromPermission(pr));
        }
        return permissionResponseDtos;
    }

    @Override
    public List<Permission> getUserPermissions(long userId) {
        return permissionRepository.getAllByUserId(userId);
    }

}
