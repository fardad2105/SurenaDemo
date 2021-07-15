package com.fy.surena.repository;

import com.fy.surena.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    @Modifying
    @Query("delete from UserInfo u where u.firstname = ?1")
    void deleteUserInfoByUserName(String username);


    @Modifying
    @Query("update UserInfo u set u.firstname=?1,u.lastname = ?2, u.ModifiedDate = ?3 where u.id = ?4 ")
    int updateUserInfo(String firstname, String lastname,String modify_date, Long id);


    @Query("select  u from UserInfo u where u.username = ?1")
    UserInfo getUserInfoByUsername(String username);


    @Modifying
    @Query("update UserInfo u set u.password = ?1 where u.password = ?2")
    void changePassword(String newPass, String oldPass);


    @Query("select count (u) = 1 from UserInfo u where u.username = ?1")
    boolean isUserExistWithUsername(String username);




}
