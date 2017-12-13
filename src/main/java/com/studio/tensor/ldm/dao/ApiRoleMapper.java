package com.studio.tensor.ldm.dao;

import com.studio.tensor.ldm.pojo.ApiRole;

public interface ApiRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApiRole record);

    int insertSelective(ApiRole record);

    ApiRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApiRole record);

    int updateByPrimaryKey(ApiRole record);
}