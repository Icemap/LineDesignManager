package com.studio.tensor.ldm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.studio.tensor.ldm.pojo.CostPriceAdjustmentIndexInfo;

public interface CostPriceAdjustmentIndexInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CostPriceAdjustmentIndexInfo record);

    int insertSelective(CostPriceAdjustmentIndexInfo record);

    CostPriceAdjustmentIndexInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CostPriceAdjustmentIndexInfo record);

    int updateByPrimaryKey(CostPriceAdjustmentIndexInfo record);
    
    List<CostPriceAdjustmentIndexInfo> selectByTag(@Param("tag")String tag);

    List<CostPriceAdjustmentIndexInfo> selectIceInfoByMsg(
    		@Param("tag")String tag, @Param("voltageLevel")Integer voltageLevel,
    		@Param("loopNum")Integer loopNum, @Param("iceHeight")Double iceHeight);
    
    List<CostPriceAdjustmentIndexInfo> selectWindInfoByMsg(
    		@Param("tag")String tag, @Param("voltageLevel")Integer voltageLevel,
    		@Param("windSpeed")Double windSpeed);

    List<CostPriceAdjustmentIndexInfo> selectHumanInfoByMsg(
    		@Param("tag")String tag, @Param("voltageLevel")Integer voltageLevel,
    		@Param("humanLength")Double humanLength);
}