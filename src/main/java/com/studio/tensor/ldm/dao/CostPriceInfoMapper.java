package com.studio.tensor.ldm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.studio.tensor.ldm.pojo.CostPriceInfo;

public interface CostPriceInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CostPriceInfo record);

    int insertSelective(CostPriceInfo record);

    CostPriceInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CostPriceInfo record);

    int updateByPrimaryKey(CostPriceInfo record);
    
    List<String> getTag();
    
    List<CostPriceInfo> selectByTag(@Param("tag")String tag);
    
    List<CostPriceInfo> selectByMsg(@Param("tag")String tag,
    		@Param("rowNum")Integer rowNum, @Param("sectionArea")Integer sectionArea,
    		@Param("loopNum")Integer loopNum, @Param("voltageLevel")Integer voltageLevel);
}