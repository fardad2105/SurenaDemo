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
    public Permission savePermission(PermissionRequestDto permission) {
        if (permissionRepository.existsByTitle(permission.getTitle())) {
            throw new PermissionManagementException(HttpStatus.CONFLICT,
                    "permission with this" + permission.getTitle() + "is Exists");
        } else {
            SimpleDateFormat create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            permission.setCreatedAt(create_date.format(now));
        }
        return permissionRepository.save(mapper.permissionGetBypermissionDto(permission));

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
    public Permission updatePermission(PermissionRequestDto permission) {
        if (!permissionRepository.existsByTitle(permission.getTitle())) {
            throw new PermissionManagementException(HttpStatus.NOT_FOUND,
                    "permission with this info not found");
        } else {
            SimpleDateFormat update_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            permission.setUpdatedAt(update_date.format(now));
            return permissionRepository.save(mapper.permissionGetBypermissionDto(permission));
        }
    }

    @Override
    public List<Permission> getPermissions() {
        return permissionRepository.findAll();
    }
}
