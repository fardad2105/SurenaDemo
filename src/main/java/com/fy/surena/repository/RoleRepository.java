package com.fy.surena.repository;

import com.fy.surena.mapstruct.dtos.request.RoleRequestDto;
import com.fy.surena.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    boolean existsByTitle(String title);
//    Role findById(int it);

}
