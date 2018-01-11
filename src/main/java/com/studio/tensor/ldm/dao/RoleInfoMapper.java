package com.studio.tensor.ldm.dao;

import java.util.List;

import com.studio.tensor.ldm.pojo.RoleInfo;

public interface RoleInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    RoleInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);
    
    List<RoleInfo> selectAll();
    
    RoleInfo selectDefaultRole();
}