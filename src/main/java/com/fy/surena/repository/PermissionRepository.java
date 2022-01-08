package com.fy.surena.repository;

import com.fy.surena.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer> {

    boolean existsByTitle(String title);

    Optional<Permission> findByTitle(String permissionKey);

    @Query(value = "select * from Permission p where p.id in" +
            " (select permission_id from role_permission where role_id in (select role_id from user_role where user_id = ?1))",nativeQuery = true)
    List<Permission> getAllByUserId(long userId);

}
