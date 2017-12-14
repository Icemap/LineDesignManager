package com.studio.tensor.ldm.dao;

import org.apache.ibatis.annotations.Param;

import com.studio.tensor.ldm.pojo.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    UserInfo userLogin(@Param("phoneNum")String phoneNum,
    		@Param("password")String password);
    
    int updatePasswordByPhone(@Param("phoneNum")String phoneNum,
    		@Param("password")String password);
}