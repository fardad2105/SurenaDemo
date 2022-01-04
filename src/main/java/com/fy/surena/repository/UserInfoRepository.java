package com.fy.surena.repository;

import com.fy.surena.model.Permission;
import com.fy.surena.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    @Modifying
    @Query("update UserInfo u set u.firstname=?1,u.lastname = ?2, u.ModifiedDate = ?3 where u.id = ?4 ")
    int updateUserInfo(String firstname, String lastname,String modify_date, Long id);

    Optional<UserInfo> findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsUserInfoByUsername(String username);




}
