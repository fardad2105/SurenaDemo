package com.fy.surena.controller;

import com.fy.surena.model.Role;
import com.fy.surena.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/security")
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/assign-role/{userId}/{roleId}")
    public ResponseEntity<String> assignUserRole(@PathVariable("userId") Long userId,
                                                 @PathVariable("roleId") int roleId) {
        securityService.assignUserRole(userId,roleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/unassign-role/{userId}/{roleId}")
    public ResponseEntity<String> unassignUserRole(@PathVariable("userId") Long userId,
                                                   @PathVariable("roleId") int roleId) {
        securityService.unassignUserRole(userId,roleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user-role/{id}")
    public ResponseEntity<Set<Role>> getUserRoles(@PathVariable("id") long id) {
        Set<Role> roles = securityService.getUserRoles(id);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/add-permission/{roleId}/{permissionId}")
    public ResponseEntity<Role> addPermissionOnRole(@PathVariable("roleId") int roleId,
                                                     @PathVariable("permissionId") int permissionId) {

        securityService.addPermissionOnRole(roleId,permissionId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/remove-permission/{roleId}/{permissionId}")
    public ResponseEntity<Role> removePermissionOnRole(@PathVariable("roleId") int roleId,
                                                       @PathVariable("permissionId") int permissionId) {
        securityService.removePermissionOnRole(roleId,permissionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
