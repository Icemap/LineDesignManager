package com.studio.tensor.ldm.dao;

import com.studio.tensor.ldm.pojo.BendingCoefficientInfo;

public interface BendingCoefficientInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(BendingCoefficientInfo record);

    int insertSelective(BendingCoefficientInfo record);

    BendingCoefficientInfo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(BendingCoefficientInfo record);

    int updateByPrimaryKey(BendingCoefficientInfo record);
}