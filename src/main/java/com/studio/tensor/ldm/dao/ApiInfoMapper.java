package com.studio.tensor.ldm.dao;

import com.studio.tensor.ldm.pojo.ApiInfo;

public interface ApiInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApiInfo record);

    int insertSelective(ApiInfo record);

    ApiInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApiInfo record);

    int updateByPrimaryKey(ApiInfo record);
}