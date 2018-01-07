package com.studio.tensor.ldm.dao;

import org.apache.ibatis.annotations.Param;

import com.studio.tensor.ldm.pojo.UserStatus;

public interface UserStatusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserStatus record);

    int insertSelective(UserStatus record);

    UserStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserStatus record);

    int updateByPrimaryKey(UserStatus record);
    
    int selectUserIdNum(Integer userId);
    
    UserStatus selectUserStatus(Integer userId);

    int updateByUserId(@Param("userId")Integer userId,
    		@Param("statusJson")String statusJson);
}