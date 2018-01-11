package com.studio.tensor.ldm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.studio.tensor.ldm.pojo.ApiRole;

public interface ApiRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApiRole record);

    int insertSelective(ApiRole record);

    ApiRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApiRole record);

    int updateByPrimaryKey(ApiRole record);
    
    List<ApiRole> selectAll();
    
    Integer selectNumByApiAndRoleId(@Param("apiId")Integer apiId, 
    		@Param("roleId")Integer roleId);
}