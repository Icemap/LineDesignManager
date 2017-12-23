package com.studio.tensor.ldm.service;

import java.util.List;

import com.studio.tensor.ldm.bean.CostPriceCalculateResultBean;
import com.studio.tensor.ldm.bean.GISTerrainBean;
import com.studio.tensor.ldm.pojo.CostPriceAdjustmentIndexInfo;
import com.studio.tensor.ldm.pojo.CostPriceInfo;

public interface CostPriceInfoService
{
	Boolean insertCostPriceInfo(CostPriceInfo costPriceInfo);
	Boolean updateCostPriceInfo(CostPriceInfo costPriceInfo);
	Boolean deleteCostPriceInfo(Integer costPriceInfoId);
	
	Boolean insertCostPriceAdjustmentIndexInfo(CostPriceAdjustmentIndexInfo costPriceAdjustmentIndexInfo);
	Boolean updateCostPriceAdjustmentIndexInfo(CostPriceAdjustmentIndexInfo costPriceAdjustmentIndexInfo);
	Boolean deleteCostPriceAdjustmentIndexInfo(Integer costPriceAdjustmentIndexInfoId);
	
	List<String> selectLibraryTag();
	List<CostPriceInfo> selectCostPriceInfoByLibraryTag(String tag);
	List<CostPriceAdjustmentIndexInfo> selectCostPriceAdjustmentIndexInfoByLibraryTag(String tag);
	CostPriceCalculateResultBean costPriceCalc(String tag, Integer rowNum, Integer sectionArea,
			Integer loopNum, Integer voltageLevel, Double iceHeight, Double windSpeed,
			Double humanLength,  List<GISTerrainBean> gisTerrainBeans);
}
